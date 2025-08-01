package gov.goias.relatorios.notificacao.commons.websocket;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketUserSessionRegistry {
    private final Map<String, String> userSessions = new ConcurrentHashMap<>();

    public void register(String userId, String sessionId) {
        userSessions.put(userId, sessionId);
    }

    public void unregisterBySession(String sessionId) {
        userSessions.values().removeIf(session -> session.equals(sessionId));
    }

    public String getSessionId(String userId) {
        return userSessions.get(userId);
    }
}
