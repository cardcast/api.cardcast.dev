package dev.cardcast.bullying.network;

import com.google.gson.JsonObject;
import dev.cardcast.bullying.network.annotations.EventHandler;
import dev.cardcast.bullying.network.messages.serverbound.ServerBoundWSMessage;
import dev.cardcast.bullying.network.messages.serverbound.lobby.SB_RequestLobbyMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NetworkService {

    static {
        NetworkService.messages.add(SB_RequestLobbyMessage.class);
    }

    private static List<Class<? extends ServerBoundWSMessage>> messages = new ArrayList<>();

    public static Class<? extends ServerBoundWSMessage> getMessageType(JsonObject json) {
        String type = json.get("type").getAsString();
        for (Class<? extends ServerBoundWSMessage> messageType : messages) {
            if (messageType.getSimpleName().equals(type)) {
                return messageType;
            }
        }
        return null;
    }


    private List<EventListener> listeners = new ArrayList<>();

    public NetworkService() {
    }

    public void registerEventListener(EventListener listenerClass) {
        this.listeners.add(listenerClass);
    }

    public static List<Method> getEventHandlerMethods(final Class<?> type) {
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

    public void handleEvent(ServerBoundWSMessage message) {
        for (EventListener listener : listeners) {
            List<Method> eventMethods = getEventHandlerMethods(listener.getClass());
            for (Method eventMethod : eventMethods) {
                try {
                    eventMethod.invoke(null, message);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
