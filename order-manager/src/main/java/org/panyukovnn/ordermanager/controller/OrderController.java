package org.panyukovnn.ordermanager.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.ordermanager.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public String createOrder() {
        orderService.processOrder();

        return "successful order";
    }
}
