package com.car.castel.CustomerValidation.event.listener;

import com.car.castel.CustomerValidation.config.RabbitEmailVeryFyConfig;
import com.car.castel.CustomerValidation.event.modal.EmailVerifiedEvent;
import com.car.castel.CustomerValidation.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailVeryFyEventListener {

    @Autowired
    private EmailSender emailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @RabbitListener(queues = RabbitEmailVeryFyConfig.QUEUE_ADMIN)
    public void consumeMessageFromQueue(EmailVerifiedEvent emailVerifiedEvent){
        log.info("Email is verified for  the driver having email : {}", emailVerifiedEvent.toString());
        emailSender.send(adminEmail,
                "A new driver is registered with email address of " + emailVerifiedEvent.getTo(),
                "A new driver is registered", null,null );
    }
}
