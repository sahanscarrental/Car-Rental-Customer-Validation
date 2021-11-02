package com.car.castel.CustomerValidation.repository;

import com.car.castel.CustomerValidation.entity.ContactUsMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContactUsMessageRepository extends JpaRepository<ContactUsMessage, UUID> {
}
