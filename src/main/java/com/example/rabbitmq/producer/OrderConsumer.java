package com.example.rabbitmq.producer;

import com.example.rabbitmq.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "inventory-queue")
    public void consumeEvent(@Payload OrderEvent event) {
        log.info("OrderEvent: {}", event);
    }
}
