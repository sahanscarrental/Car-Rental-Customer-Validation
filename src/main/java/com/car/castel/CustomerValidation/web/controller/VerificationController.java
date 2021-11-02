package com.car.castel.CustomerValidation.web.controller;

import com.car.castel.CustomerValidation.entity.OTPRequest;
import com.car.castel.CustomerValidation.repository.OTPRequestRepository;
import com.car.castel.CustomerValidation.service.OTPService;
import com.car.castel.CustomerValidation.web.exception.EntityNotFoundException;
import com.car.castel.CustomerValidation.web.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/otp")
public class VerificationController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private OTPRequestRepository otpRequestRepository;

    @PostMapping("/request")
    public ResponseEntity<ApiResponse> requestOTP(@NotNull @QueryParam(value = "to") String to) throws Exception{

        Optional<OTPRequest> by_to = otpRequestRepository.findBy_to(to);
        if (!by_to.isPresent()) {
            throw  new EntityNotFoundException(OTPRequest.class, " email ", to);
        }
        otpService.send(to);
        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(true)
                .timestamp(new Date())
                .body("Sent")
                .build());
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> otpSend(@NotNull @QueryParam(value = "to") String to) throws Exception{
        otpService.send(to);
        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(true)
                .timestamp(new Date())
                .body("Sent")
                .build());
    }
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse> otpVerify(@QueryParam(value = "to") String to, @QueryParam(value = "otp") Long otp) throws Exception{

        Boolean valid = otpService.isValid(to, otp);
        return ResponseEntity.ok(ApiResponse
                .builder()
                .status(true)
                .timestamp(new Date())
                .body(valid)
                .build());
    }
}
