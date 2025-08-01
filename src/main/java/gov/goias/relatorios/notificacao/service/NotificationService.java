package gov.goias.relatorios.notificacao.service;

import gov.goias.relatorios.notificacao.dto.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void receive(NotificationMessage notification) {
        log.info("Notificação Recebida = {}", notification);
        String userId = notification.getCodgUsuario();
        String mensagem = notification.getMessage();
        try {
            // Enviar para destino individual
            messagingTemplate.convertAndSend("/topic/usuario/" + userId, mensagem);
        } catch (Exception e) {
            log.error("Falha ao enviar notificação para usuário {}", userId, e);
        }
    }
}