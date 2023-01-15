package org.panyukovnn.ordermanager.client;

import org.panyukovnn.ordermanager.model.NotifyEventRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "notificationmanager", url = "http://localhost:7070/")
public interface NotificationManagerClient {

    @PostMapping("/notify")
    String postNotify(@RequestBody NotifyEventRequest notifyEventRequest);
}
