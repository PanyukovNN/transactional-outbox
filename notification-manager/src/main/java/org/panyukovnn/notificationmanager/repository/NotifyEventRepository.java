package org.panyukovnn.notificationmanager.repository;

import org.panyukovnn.notificationmanager.model.NotifyEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotifyEventRepository extends JpaRepository<NotifyEvent, UUID> {

    boolean existsByOrderId(UUID orderId);
}
