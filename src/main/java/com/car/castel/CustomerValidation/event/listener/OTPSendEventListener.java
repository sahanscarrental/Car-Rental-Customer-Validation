package com.car.castel.CustomerValidation.event.listener;

import com.car.castel.CustomerValidation.config.RabbitOTPRequestQConfig;
import com.car.castel.CustomerValidation.event.modal.OTPSendEvent;
import com.car.castel.CustomerValidation.service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OTPSendEventListener {

    @Autowired
    private OTPService otpService;

    @RabbitListener(queues = RabbitOTPRequestQConfig.QUEUE)
    public void consumeMessageFromQueue(OTPSendEvent otpSendEvent){
        log.info("OTP sending request --------------- : {}", otpSendEvent);
        otpService.send(otpSendEvent.getTo());
    }
}
