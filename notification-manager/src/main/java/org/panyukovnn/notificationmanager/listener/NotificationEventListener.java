package org.panyukovnn.notificationmanager.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.panyukovnn.notificationmanager.model.NotifyEvent;
import org.panyukovnn.notificationmanager.model.NotifyEventRequest;
import org.panyukovnn.notificationmanager.repository.NotifyEventRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    public static final String NOTIFY_CLIENT_TOPIC = "notify_client_topic";

    private final ObjectMapper objectMapper;
    private final NotifyEventRepository notifyEventRepository;

    @KafkaListener(topics = NOTIFY_CLIENT_TOPIC, groupId = "some_group")
    public void listenGroupFoo(String message) throws JsonProcessingException {
        NotifyEventRequest notifyEventRequest = objectMapper.readValue(message, new TypeReference<>() {
        });

        boolean eventExists = notifyEventRepository.existsByOrderId(notifyEventRequest.getOrderId());

        if (eventExists) {
            return;
        }

        NotifyEvent notifyEvent = NotifyEvent.builder()
                .orderId(notifyEventRequest.getOrderId())
                .build();

        notifyEventRepository.save(notifyEvent);
    }
}
