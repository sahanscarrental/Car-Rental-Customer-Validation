package com.car.castel.CustomerValidation.service.impl;

import com.car.castel.CustomerValidation.entity.OTPRequest;
import com.car.castel.CustomerValidation.event.EventHandlerProvider;
import com.car.castel.CustomerValidation.event.EventInvoker;
import com.car.castel.CustomerValidation.event.EventType;
import com.car.castel.CustomerValidation.event.modal.EmailVerifiedEvent;
import com.car.castel.CustomerValidation.repository.OTPRequestRepository;
import com.car.castel.CustomerValidation.service.EmailSender;
import com.car.castel.CustomerValidation.service.OTPService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private EmailSender emailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    private OTPRequestRepository otpRequestRepository;


    /**
     * holds set of post events and invoke when
     * invoke is called
     */
    private EventInvoker eventInvoker;

    /**
     * provide  event brokers
     */
    @Autowired
    EventHandlerProvider eventHandlerProvider;

    @Override
    public Long generate() {
        long leftLimit = 1000L;
        long rightLimit = 9999L;
        return leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
    }

    @Override
    public Boolean isValid(String to, Long otp) {
        if (to.split("@").length > 0){
            Optional<OTPRequest> optionalOTPRequest = otpRequestRepository.findBy_to(to);
            if (optionalOTPRequest.isPresent()){
                Boolean equals = otp.equals(optionalOTPRequest.get().getOtp());
                if (equals){
                    OTPRequest otpRequest = optionalOTPRequest.get();
                    otpRequest.setConfirmedAt(new Date());
                    otpRequestRepository.save(otpRequest);
                    EmailVerifiedEvent verifiedEvent = EmailVerifiedEvent.builder().to(otpRequest.get_to()).build();
                    eventInvoker = new EventInvoker();
                    eventInvoker.addEvent(eventHandlerProvider.getBroker(EventType.EMAIL_VERIFIED, verifiedEvent));
                    eventInvoker.invoke();
                }
                return equals;
            }else {
                // TODO:
            }
        }else {

        }
        return null;
    }

    @Override
    public void send(String to) {
        log.info("sending OTP to {}", to);
        Long otp = this.generate();
        if (to.split("@").length > 0){
            LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(10, ChronoUnit.MINUTES));
            Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            Optional<OTPRequest> optionalOTPRequest = otpRequestRepository.findBy_to(to);
            OTPRequest otpRequest;
            if (optionalOTPRequest.isPresent()){
                otpRequest = optionalOTPRequest.get();
                otpRequest.setExpiresAt(tmfn);
                otpRequest.setOtp(otp);
            }else {
                otpRequest = OTPRequest
                        .builder()
                        ._to(to)
                        .expiresAt(tmfn)
                        .otp(otp)
                        .build();
            }
            otpRequestRepository.save(otpRequest);
            emailSender.send(to, otp.toString(), "OTP for email verification", null, null);
        }else {
            System.out.println("------------");
        }
    }
}
