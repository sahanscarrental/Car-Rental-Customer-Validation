package com.car.castel.CustomerValidation.web.controller;


import com.car.castel.CustomerValidation.entity.ContactUsMessage;
import com.car.castel.CustomerValidation.repository.ContactUsMessageRepository;
import com.car.castel.CustomerValidation.service.EmailSender;
import com.car.castel.CustomerValidation.web.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact-us")
public class ContactUsMessageController {

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    private ContactUsMessageRepository contactUsMessageRepository;

    @Autowired
    private EmailSender emailSender;

    @PostMapping
    public ResponseEntity<ApiResponse> create( @RequestBody ContactUsMessage contactUsMessage){

        this.emailSender.send(adminEmail,contactUsMessage.getMessage(), contactUsMessage.getSubject(),null, null);
        return ResponseEntity.ok(
                ApiResponse
                        .builder()
                        .status(true)
                        .body(contactUsMessageRepository.save(contactUsMessage))
                        .build()
        );
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAll( ){
        return ResponseEntity.ok(
                ApiResponse
                        .builder()
                        .status(true)
                        .body(contactUsMessageRepository.findAll())
                        .build()
        );
    }
}
