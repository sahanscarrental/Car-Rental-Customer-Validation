package com.car.castel.CustomerValidation.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OTPRequest {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Column()
    @DateTimeFormat(pattern = "yyyy-MM-ddTH:ms")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmedAt;

    @Column()
    @DateTimeFormat(pattern = "yyyy-MM-ddTH:ms")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresAt;

    @Column()
    private Long otp;

    @Column()
    private String _to;


}
