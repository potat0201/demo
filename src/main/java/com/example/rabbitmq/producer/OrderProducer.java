package com.example.rabbitmq.producer;

import com.example.rabbitmq.dto.OrderEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service

public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createOrder(OrderEvent event) {
        rabbitTemplate.convertAndSend("order-exchange", "order.created", event);
    }
}
