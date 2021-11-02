package com.car.castel.CustomerValidation.event.listener;

import com.car.castel.CustomerValidation.config.CustomerBookingConfig;
import com.car.castel.CustomerValidation.event.modal.CustomerBookingEvent;
import com.car.castel.CustomerValidation.service.AttachmentService;
import com.car.castel.CustomerValidation.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;

@Component
public class customerBookingEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(customerBookingEventListener.class);

    @Autowired
    private EmailSender emailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    private AttachmentService attachmentService;

    @RabbitListener(queues = CustomerBookingConfig.QUEUE)
    public void consumeMessageFromQueue(CustomerBookingEvent customerBookingEvent) {
        LOGGER.info("Customer Booking event detected : {}", customerBookingEvent.toString());
        String emailBody = null;
        String emailSubject = null;

        if (customerBookingEvent.getBookingEventType() != null) {
            switch (customerBookingEvent.getBookingEventType()) {
                case BOOKING_CREATED:
                    emailBody = "<html>Dear Sir / Madam, <br>" +
                            "Booking Is <strong>Created</strong> Successfully for" +
                            "<ul>" +
                            "<li>" + "Name: " + customerBookingEvent.getCustomerName() + "</li>" +
                            "<li>" + "Vehicle Reg no: " + customerBookingEvent.getRegNo() + "</li>" +
                            "<li>" + "Time: " + customerBookingEvent.getTime() + "</li>" +
                            "</ul>";
                    emailSubject = "Booking Created";
                    break;
                case BOOKING_CANCELLED:
                    emailBody = "<html>Dear Sir / Madam, <br>" +
                            "Booking Is <strong>Cancelled</strong> for" +
                            "<ul>" +
                            "<li>" + "Name: " + customerBookingEvent.getCustomerName() + "</li>" +
                            "<li>" + "Vehicle Reg no: " + customerBookingEvent.getRegNo() + "</li>" +
                            "<li>" + "Time: " + customerBookingEvent.getTime() + "</li>" +
                            "</ul>";
                    emailSubject = "Booking Cancelled";
                    break;
                case VEHICLE_PICKED_UP:
                    emailBody = "<html>Dear Sir / Madam, <br>" +
                            "Vehicle is <strong>Picked Up</strong>" +
                            "<ul>" +
                            "<li>" + "Name: " + customerBookingEvent.getCustomerName() + "</li>" +
                            "<li>" + "Vehicle Reg no: " + customerBookingEvent.getRegNo() + "</li>" +
                            "<li>" + "Time: " + customerBookingEvent.getTime() + "</li>" +
                            "</ul>";
                    emailSubject = "Vehicle Picked Up";
                    break;
                case VEHICLE_DROP_OFF:
                    emailBody = "<html>Dear Sir / Madam, <br>" +
                            "Vehicle is <strong>Dropped Off</strong>" +
                            "<ul>" +
                            "<li>" + "Name: " + customerBookingEvent.getCustomerName() + "</li>" +
                            "<li>" + "Vehicle Reg no: " + customerBookingEvent.getRegNo() + "</li>" +
                            "<li>" + "Time: " + customerBookingEvent.getTime() + "</li>" +
                            "</ul>";
                    emailSubject = "Vehicle Drop Off";
                    break;
            }
        }
        LOGGER.info("email body: " + emailBody);
        LOGGER.info("email subject: " + emailSubject);

        // get the attachment
        HashMap apiResponse = (HashMap) attachmentService.getAttachment(customerBookingEvent.getVehicleImageId());

        String data = (String) apiResponse.get("body");

        byte[] imageData = Base64.getDecoder().decode(data);
        if (imageData != null) {
            emailBody = emailBody + "<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>";
        }
        emailBody = emailBody + "Thank You !<br> </html>";

        if (customerBookingEvent.getCustomerEmail() != null && !customerBookingEvent.getCustomerEmail().isEmpty()) {
            emailSender.send(customerBookingEvent.getCustomerEmail(),
                    emailBody,
                    emailSubject, imageData, "vehicle.png");
        } else {
            LOGGER.error("Didn't send the email since customer email is unavailable");
        }

       /* emailSender.send(adminEmail,
                emailBody,
                emailSubject, null);*/
    }


}
