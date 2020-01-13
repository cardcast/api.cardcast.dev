package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.GameManager;
import dev.cardcast.bullying.entities.Device;
import dev.cardcast.bullying.entities.Player;
import dev.cardcast.bullying.entities.PlayerContainer;
import dev.cardcast.bullying.listeners.GameListener;
import dev.cardcast.bullying.network.events.Event;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.host.SB_HostKickPlayerMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.host.SB_HostStartGameMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.lobby.SB_UserCreateGameMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.player.SB_PlayerDrawCardMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.player.SB_PlayerJoinMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.player.SB_PlayerPassTurnMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.player.SB_PlayerPlayCardMessage;
import lombok.Getter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.Session;
import javax.websocket.server.ServerContainer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetworkService {

    public static NetworkService INSTANCE;

    private List<EventListener> listeners = new ArrayList<>();

    @Getter
    private final List<Device> devices = new ArrayList<>();


    public NetworkService() {
        INSTANCE = this;
        Bullying.getLogger().info("STARTED NETWORK SERVICE");

        NetworkService.MESSAGETYPES.add(SB_PlayerJoinMessage.class);
        NetworkService.MESSAGETYPES.add(SB_PlayerDrawCardMessage.class);
        NetworkService.MESSAGETYPES.add(SB_PlayerPlayCardMessage.class);
        NetworkService.MESSAGETYPES.add(SB_UserCreateGameMessage.class);
        NetworkService.MESSAGETYPES.add(SB_HostKickPlayerMessage.class);
        NetworkService.MESSAGETYPES.add(SB_HostStartGameMessage.class);
        NetworkService.MESSAGETYPES.add(SB_PlayerPassTurnMessage.class);

        Server webSocketServer = new Server();
        ServerConnector connector = new ServerConnector(webSocketServer);
        connector.setPort(6969);
        webSocketServer.addConnector(connector);

        ServletContextHandler webSocketContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        webSocketContext.setContextPath("/");
        webSocketServer.setHandler(webSocketContext);

        this.registerEventListener(new GameListener());

        try {
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(webSocketContext);
            wscontainer.addEndpoint(GameConnector.class);
            webSocketServer.start();
            webSocketServer.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    private static List<Class<? extends ServerBoundWSMessage>> MESSAGETYPES = new ArrayList<>();

    public void registerEventListener(EventListener listenerClass) {
        this.listeners.add(listenerClass);
    }

    private static List<Method> getEventHandlerMethods(final Class<?> type) {
        final List<Method> methods = new ArrayList<>();
        Class<?> klass = type;
        while (klass != Object.class) {
            final List<Method> allMethods = new ArrayList<>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    methods.add(method);
                }
            }
            klass = klass.getSuperclass();
        }
        return methods;
    }

    void handleEvent(Session session, ServerBoundWSMessage message) {
        Event event = message.getEvent();
        for (EventListener listener : this.listeners) {
            List<Method> eventMethods = getEventHandlerMethods(listener.getClass());
            for (Method eventMethod : eventMethods) {
                if (Arrays.stream(eventMethod.getParameters()).anyMatch(parameter -> parameter.getType() == event.getClass())) {
                    try {
                        eventMethod.invoke(listener, session, event);
                            Bullying.getLogger().info("NEW INCOMING EVENT: " + event.getClass());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static Pattern messagePattern = Pattern.compile("^SB_(?<messageName>[A-Za-z]+)Message$");

    static Class<? extends ServerBoundWSMessage> getMessageEvent(JsonObject json) {
        String type = json.get("type").getAsString();
        for (Class<? extends ServerBoundWSMessage> messageType : MESSAGETYPES) {
            Matcher matcher = messagePattern.matcher(messageType.getSimpleName());
            if (matcher.matches() && matcher.group("messageName").equals(type)) {
                return messageType;
            }
        }
        return null;
    }

    public void registerPlayer(UUID uuid, Session session) {
        this.getDevices().add(new Player(uuid, session));
    }

    public Device getDeviceByUuid(UUID uuid) {
        for (Device device : this.getDevices()) {
            if (device.getUuid().equals(uuid)) {
                return device;
            }
        }
        return null;
    }

    public Device getDeviceBySession(Session session) {
        for (Device device : this.getDevices()) {
            if (device.getSession().getId().equals(session.getId())) {
                return device;
            }
        }
        return null;
    }
}
