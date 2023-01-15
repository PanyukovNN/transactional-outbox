package org.panyukovnn.ordermanager.service;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.ordermanager.model.Order;
import org.panyukovnn.ordermanager.model.OrderOutbox;
import org.panyukovnn.ordermanager.repository.OrderOutboxRepository;
import org.panyukovnn.ordermanager.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderOutboxRepository orderOutboxRepository;

    @Transactional
    public Order processOrder() {
        Order order = new Order();

        orderRepository.save(order);

        orderOutboxRepository.save(OrderOutbox.builder()
                .order(order)
                .build());

        return order;
    }
}
