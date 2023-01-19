package org.panyukovnn.notificationmanager.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.panyukovnn.notificationmanager.model.DebeziumMessage;
import org.panyukovnn.notificationmanager.model.NotifyEvent;
import org.panyukovnn.notificationmanager.model.NotifyEventRequest;
import org.panyukovnn.notificationmanager.repository.NotifyEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    public static final String NOTIFY_CLIENT_TOPIC = "postgres.order-manager.order_outbox";
    public static final String CREATE_OPERATION_CODE = "c";

    private final ObjectMapper objectMapper;
    private final NotifyEventRepository notifyEventRepository;

    @KafkaListener(topics = NOTIFY_CLIENT_TOPIC)
    public void listen(@Payload(required = false) String message) throws JsonProcessingException {
        if (!StringUtils.hasText(message)) {
            return;
        }

        DebeziumMessage debeziumMessage = objectMapper.readValue(message, new TypeReference<>() {
        });

        if (!CREATE_OPERATION_CODE.equals(debeziumMessage.getOperation())) {
            return;
        }

        NotifyEventRequest notifyEventRequest = debeziumMessage.getNotifyEventRequest();

        if (notifyEventRequest == null) {
            log.error("notifyEventRequest is null");

            return;
        }

        log.info("Notification event: " + notifyEventRequest);

        boolean eventExists = notifyEventRepository.existsByOrderId(notifyEventRequest.getOrderId());

        if (eventExists) {
            log.info("Client already notified");

            return;
        }

        notifyEventRepository.save(NotifyEvent.builder()
                .orderId(notifyEventRequest.getOrderId())
                .build());

        log.info("Client notified");
    }
}
