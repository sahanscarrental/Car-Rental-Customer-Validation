package com.car.castel.CustomerValidation.service;

public interface OTPService {
    Long generate();
    Boolean isValid(String to, Long otp);
    void send(String to);
}
