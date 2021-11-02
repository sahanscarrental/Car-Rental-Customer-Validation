package com.car.castel.CustomerValidation.repository;

import com.car.castel.CustomerValidation.entity.OTPRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OTPRequestRepository extends JpaRepository<OTPRequest, UUID> {
    Optional<OTPRequest> findBy_to(String to);
}
