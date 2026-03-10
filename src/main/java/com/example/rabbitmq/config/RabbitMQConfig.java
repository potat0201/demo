package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Đặt tên cho các thành phần để dễ gọi lại sau này
    public static final String EXCHANGE = "order-exchange";
    public static final String INVENTORY_QUEUE = "inventory-queue";
    public static final String NOTIFICATION_QUEUE = "notification-queue";

    // 1. Tạo "Nhân viên phân loại" (Topic Exchange)
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE);
    }

    // 2. Tạo "Hòm thư" cho bộ phận Kho (Inventory)
    @Bean
    public Queue inventoryQueue() {
        return new Queue(INVENTORY_QUEUE);
    }

    // 3. Tạo "Hòm thư" cho bộ phận Thông báo (Notification)
    @Bean
    public Queue notificationQueue() {
        return new Queue(NOTIFICATION_QUEUE);
    }

    // 4. Đặt luật cho bộ phận Kho: Chỉ nhận thư có chữ "order.created"
    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(inventoryQueue).to(orderExchange).with("order.created");
    }

    // 5. Đặt luật cho bộ phận Thông báo: Nhận MỌI thư bắt đầu bằng "order."
    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(notificationQueue).to(orderExchange).with("order.*");
    }

    // 6. Cấu hình "Người phiên dịch" (Chuyển đổi Object thành JSON)
    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            JacksonJsonMessageConverter converter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}