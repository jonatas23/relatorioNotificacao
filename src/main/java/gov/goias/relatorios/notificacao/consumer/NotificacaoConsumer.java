package gov.goias.relatorios.notificacao.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gov.goias.relatorios.notificacao.dto.NotificationMessage;
import gov.goias.relatorios.notificacao.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificacaoConsumer {

    private final NotificationService notificationService;

    public NotificacaoConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "${kafka.topic.relatorio.notificacao}", groupId = "relatorio-group")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2))
    public void consumir(ConsumerRecord<String, String> record) {
        log.info("Chave = {}", record.key());
        log.info("Cabecalho = {}", record.headers());
        log.info("Particao = {}", record.partition());

        String strDados = record.value();

        ObjectMapper mapper = new ObjectMapper();
        NotificationMessage notification;

        try {
            notification = mapper.readValue(strDados, NotificationMessage.class);
        } catch (JsonProcessingException ex) {
            log.error("Falha converter Notificação [dado={}}]", strDados, ex);
            return;
        }

        this.notificationService.receive(notification);
    }

}