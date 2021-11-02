package com.car.castel.CustomerValidation.event.handler;

import com.car.castel.CustomerValidation.event.Event;
import com.car.castel.CustomerValidation.event.modal.EmailVerifiedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


@Slf4j
public class EmailVerifyEventHandler implements Event {

    private final String exchange;
    private final String routingKey;

    private final RabbitTemplate rabbitTemplate;
    private final EmailVerifiedEvent emailVerifiedEvent;

    public EmailVerifyEventHandler(EmailVerifiedEvent event, RabbitTemplate template, String routingKey, String exchange) {
        this.rabbitTemplate = template;
        this.emailVerifiedEvent = event;
        this.routingKey = routingKey;
        this.exchange = exchange;
    }

    @Override
    public void execute() {
        log.info("executing the EmailVerifyEventBroker to " + emailVerifiedEvent.toString());
        log.info("routing key: {} , exchange: {}", routingKey, exchange);
        try {
            rabbitTemplate.convertAndSend(exchange, routingKey,emailVerifiedEvent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
