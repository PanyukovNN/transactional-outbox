package org.panyukovnn.notificationmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;

@Data
public class DebeziumMessage {

    @JsonProperty("after")
    private NotifyEventRequest notifyEventRequest;

    private String operation;

    @JsonProperty("payload")
    private void unpackPayload(Map<String, Object> payload) {
        this.operation = (String) payload.get("op");
        //noinspection unchecked
        Map<String, String> flattenEventRequest = (Map<String, String>) payload.get("after");
        if (flattenEventRequest != null) {
            String orderId = flattenEventRequest.get("order_id");

            if (StringUtils.hasText(orderId)) {
                notifyEventRequest = new NotifyEventRequest(UUID.fromString(orderId));
            }
        }
    }
}
