package com.car.castel.CustomerValidation.service.impl;

import com.car.castel.CustomerValidation.service.AttachmentService;
import com.car.castel.CustomerValidation.web.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AttachmentService.class);


    public static final String FILE_SERVICE_HOST = "http://127.0.0.1:9191/file-service/api/image/email-image";

    @Override
    public Object getAttachment(UUID imageId) {

        try {
            URI uri = UriComponentsBuilder.fromUriString(FILE_SERVICE_HOST+"/"+imageId)
                    .build()
                    .toUri();
            LOGGER.info(uri.toString());
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = restTemplate.getForEntity(uri ,Object.class);
            return response.getBody();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
