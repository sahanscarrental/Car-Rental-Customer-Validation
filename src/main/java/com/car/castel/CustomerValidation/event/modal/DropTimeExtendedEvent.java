package com.car.castel.CustomerValidation.event.modal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DropTimeExtendedEvent {
    private String driverEmail;
    private String driverPhoneNo;
    private String driverName;
    private String vehicleNo;
    private Date extendedTo;

    public String getEmailBody(){
        return "<ul>" +
                "<li> Name: " + driverName + "</li>" +
                "<li> Email: " + driverEmail +"</li>" +
                "<li> Phone No: " + driverPhoneNo + "</li>" +
                "<li> vehicle No: " + vehicleNo + "</li>" +
                "<li> extended To:" + extendedTo + "</li>" +
                "</ul>";
    }

    @Override
    public String toString() {
        return "DropTimeExtendedEvent{" +
                "driverEmail='" + driverEmail + '\'' +
                ", driverPhoneNo='" + driverPhoneNo + '\'' +
                ", driverName='" + driverName + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", extendedTo=" + extendedTo +
                '}';
    }
}
