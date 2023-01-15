package org.panyukovnn.ordermanager.controller;

import lombok.RequiredArgsConstructor;
import org.panyukovnn.ordermanager.service.OrderNotifyService;
import org.panyukovnn.ordermanager.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderNotifyService notifyService;

    @PostMapping("/order")
    public String createOrder() {
        orderService.processOrder();

        notifyService.startNotify();

        return "successful order";
    }
}
