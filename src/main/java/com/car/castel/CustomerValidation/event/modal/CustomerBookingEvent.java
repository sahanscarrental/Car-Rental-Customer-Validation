package com.car.castel.CustomerValidation.event.modal;

import com.car.castel.CustomerValidation.event.BookingEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CustomerBookingEvent {
    private String regNo;
    private Date time;
    private UUID vehicleImageId;
    private String customerName;
    private String customerEmail;
    private BookingEventType bookingEventType;

}
