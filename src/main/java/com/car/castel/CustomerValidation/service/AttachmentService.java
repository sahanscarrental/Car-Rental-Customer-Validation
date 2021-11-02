package com.car.castel.CustomerValidation.service;

import com.car.castel.CustomerValidation.web.dto.response.ApiResponse;

import java.util.UUID;

public interface AttachmentService {

    /**
     * Get the image from the file Service
     * @param imageId
     * @return
     */
    Object getAttachment(UUID imageId);
}
