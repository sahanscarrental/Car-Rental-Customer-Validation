package com.car.castel.CustomerValidation.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Test {

    @Value("${spring.mail.username}")
    private String emailAuthor;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @GetMapping
    public String test(){
        return "working";
    }
}
