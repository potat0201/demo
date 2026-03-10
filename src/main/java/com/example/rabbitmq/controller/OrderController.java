package com.example.rabbitmq.controller;

import com.example.rabbitmq.dto.OrderEvent;
import com.example.rabbitmq.producer.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/test")
    public void createOrder(@RequestBody OrderEvent event) {
        orderProducer.createOrder(event);
    }
}
