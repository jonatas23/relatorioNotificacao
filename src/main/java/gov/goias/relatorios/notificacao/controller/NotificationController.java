package gov.goias.relatorios.notificacao.controller;

import gov.goias.relatorios.notificacao.commons.websocket.WebSocketUserSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @Autowired
    private WebSocketUserSessionRegistry sessionRegistry;

    @MessageMapping("/register")
    public void register(@Header("simpSessionId") String sessionId, @Payload String codgUsuario) {
        sessionRegistry.register(codgUsuario, sessionId);
    }
}
