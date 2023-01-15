package org.panyukovnn.ordermanager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.panyukovnn.ordermanager.model.NotifyEventRequest;
import org.panyukovnn.ordermanager.repository.OrderOutboxRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static org.panyukovnn.ordermanager.config.KafkaTopicConfig.NOTIFY_CLIENT_TOPIC;

@Service
@RequiredArgsConstructor
public class OrderNotifyService {

    private final ObjectMapper objectMapper;
    private final OrderOutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void startNotify() {
        outboxRepository.findAll()
                .forEach(orderOutbox -> {
                    NotifyEventRequest notifyEventRequest = NotifyEventRequest.builder()
                            .orderId(orderOutbox.getOrder().getId())
                            .build();

                    kafkaTemplate.send(NOTIFY_CLIENT_TOPIC, writeValueAsString(notifyEventRequest));

                    outboxRepository.delete(orderOutbox);
                });
    }

    @SneakyThrows
    private <T> String writeValueAsString(T obj) {
        return objectMapper.writeValueAsString(obj);
    }
}
