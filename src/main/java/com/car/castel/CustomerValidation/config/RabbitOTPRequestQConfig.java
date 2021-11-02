package com.car.castel.CustomerValidation.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitOTPRequestQConfig {

    @Value("${exchange.otp-send}")
    private String exchange;

    @Value("${routingKey.otp-send}")
    private String routingKey;

    public static final String QUEUE = "otp_send_queue";

    @Bean
    public Queue newMessageQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public TopicExchange newMessageExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding newMessageBinding(
            @Qualifier("newMessageQueue") Queue newMessageQueue,
            @Qualifier("newMessageExchange") TopicExchange newMessageExchange
    ) {
        return BindingBuilder.bind(newMessageQueue).to(newMessageExchange).with(routingKey);
    }
}
