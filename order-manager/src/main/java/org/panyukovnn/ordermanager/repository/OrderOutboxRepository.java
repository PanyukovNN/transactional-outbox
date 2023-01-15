package org.panyukovnn.ordermanager.repository;

import org.panyukovnn.ordermanager.model.OrderOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderOutboxRepository extends JpaRepository<OrderOutbox, UUID> {

    Optional<OrderOutbox> findByOrderId(UUID orderId);

}
