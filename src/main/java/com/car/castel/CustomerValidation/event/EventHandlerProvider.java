package com.car.castel.CustomerValidation.event;


import com.car.castel.CustomerValidation.event.handler.EmailVerifyEventHandler;
import com.car.castel.CustomerValidation.event.modal.EmailVerifiedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventHandlerProvider {
    @Autowired
    private RabbitTemplate template;

    @Value("${exchange.email-verify}")
    private String emailVerifyExchange;

    @Value("${routingKey.email-verify}")
    private String emailVerifyRoutingKey;

    public Event getBroker(EventType eventType, Object event){
        switch (eventType){
            case EMAIL_VERIFIED:
            default:
                return new EmailVerifyEventHandler((EmailVerifiedEvent) event, template, emailVerifyRoutingKey, emailVerifyExchange);
        }
    }
}
