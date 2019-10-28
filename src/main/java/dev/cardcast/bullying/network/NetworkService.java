package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import dev.cardcast.bullying.Bullying;
import dev.cardcast.bullying.listeners.GameListener;
import dev.cardcast.bullying.network.events.annotations.EventHandler;
import dev.cardcast.bullying.network.events.EventListener;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.SB_PlayerDrawCardMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.SB_PlayerPlayCardMessage;
import dev.cardcast.bullying.network.messages.serverbound.game.SB_PlayerReadyUpMessage;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;


import javax.websocket.Session;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkService {
    private static List<Class<? extends ServerBoundWSMessage>> messages = new ArrayList<>();
    public static NetworkService INSTANCE;

    static Class<? extends ServerBoundWSMessage> getMessageEvent(JsonObject json) {
        String type = json.get("type").getAsString();
        for (Class<? extends ServerBoundWSMessage> messageType : messages) {
            String simpleName = messageType.getSimpleName();
            simpleName = simpleName.substring(3);
            simpleName = simpleName.substring(0, simpleName.length() - 7);
            if (simpleName.equals(type)) {
                return messageType;
            }
        }
        return null;
    }


    private List<EventListener> listeners = new ArrayList<>();

    public NetworkService() {
        INSTANCE = this;
        Bullying.getLogger().info("STARTED NETWORK SERVICE");

        NetworkService.messages.add(SB_PlayerReadyUpMessage.class);
        NetworkService.messages.add(SB_PlayerDrawCardMessage.class);
        NetworkService.messages.add(SB_PlayerPlayCardMessage.class);

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
        for (EventListener listener : listeners) {
            List<Method> eventMethods = getEventHandlerMethods(listener.getClass());
            for (Method eventMethod : eventMethods) {
                try {
                    eventMethod.invoke(listener, session, message.getEvent());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
