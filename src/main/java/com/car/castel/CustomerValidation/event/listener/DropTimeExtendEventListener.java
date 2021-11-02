package com.car.castel.CustomerValidation.event.listener;

import com.car.castel.CustomerValidation.config.RabbitDropTimeExtendedConfig;
import com.car.castel.CustomerValidation.event.modal.DropTimeExtendedEvent;
import com.car.castel.CustomerValidation.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DropTimeExtendEventListener {
    @Autowired
    private EmailSender emailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @RabbitListener(queues = RabbitDropTimeExtendedConfig.QUEUE)
    public void consumeMessageFromQueue(DropTimeExtendedEvent dropTimeExtendedEvent){
        log.info("Vehicle drop time extend request : {}", dropTimeExtendedEvent.toString());

        emailSender.send(adminEmail,
                "A request to extend drop time with following details, \n \n" + dropTimeExtendedEvent.getEmailBody(),
                "Vehicle drop time extend request" , null, null);
    }
}
