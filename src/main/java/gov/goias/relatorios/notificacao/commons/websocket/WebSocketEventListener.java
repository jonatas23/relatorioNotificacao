package gov.goias.relatorios.notificacao.commons.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        log.info("Conexão estabelecida: " + event.getMessage());
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("Conexão encerrada. SessionId: " + event.getSessionId());
    }
}
