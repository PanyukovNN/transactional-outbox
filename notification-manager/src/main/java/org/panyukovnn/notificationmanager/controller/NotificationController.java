package org.panyukovnn.notificationmanager.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.notificationmanager.model.NotifyEvent;
import org.panyukovnn.notificationmanager.model.NotifyEventRequest;
import org.panyukovnn.notificationmanager.repository.NotifyEventRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final NotifyEventRepository notifyEventRepository;

    @PostMapping("/notify")
    public String postNotify(@RequestBody NotifyEventRequest notifyEventRequest) {
        boolean eventExists = notifyEventRepository.existsByOrderId(notifyEventRequest.getOrderId());

        if (eventExists) {
            return "Already notified";
        }

        NotifyEvent notifyEvent = NotifyEvent.builder()
                .orderId(notifyEventRequest.getOrderId())
                .build();

        notifyEventRepository.save(notifyEvent);

        return "Notification successful";
    }
}
