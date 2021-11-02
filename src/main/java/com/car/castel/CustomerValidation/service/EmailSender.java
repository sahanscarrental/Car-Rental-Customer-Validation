package com.car.castel.CustomerValidation.service;

public interface EmailSender {
    void send(String to, String body, String subject, byte[] attachment, String imageNameWithExtension);
}
