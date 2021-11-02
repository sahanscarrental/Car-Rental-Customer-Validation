package com.car.castel.CustomerValidation.event.listener;

import com.car.castel.CustomerValidation.config.RabbitDMVSuspendedDriverConfig;
import com.car.castel.CustomerValidation.event.modal.DMVSuspendedDriverEvent;
import com.car.castel.CustomerValidation.service.AttachmentService;
import com.car.castel.CustomerValidation.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Slf4j
@Component
public class DMVSuspendedDriverEventListener {
    @Autowired
    private EmailSender emailSender;

    @Autowired
    private AttachmentService attachmentService;

    @Value("${admin.dmv-email}")
    private String dmvEmail;

    @Value("${admin.email}")
    private String adminEmail;

    @RabbitListener(queues = RabbitDMVSuspendedDriverConfig.QUEUE)
    public void consumeMessageFromQueue(DMVSuspendedDriverEvent dmvSuspendedDriverEvent){
        log.info("DMV suspended driver detected : {}", dmvSuspendedDriverEvent.toString());

        try {
            HashMap apiResponse = (HashMap) attachmentService.getAttachment(dmvSuspendedDriverEvent.getFrontImageId());
            String data = (String)apiResponse.get("body");

            byte[] imageData = Base64.getDecoder().decode(data);
            // creating the email body to display the information with inline image for driver's license
            StringBuffer stringBuffer
                    = new StringBuffer("<html>Dear Sir / Madam, <br>");
            stringBuffer.append("This is Banger & Co. Car rental Company with Registration no. of 0025. <br>");
            stringBuffer.append("Today "+ (new Date()).toString()+", we became aware that the Driver <strong>"+dmvSuspendedDriverEvent.getRegNo()+"</strong> in your list of Licenses <br>");
            stringBuffer.append("that are currently; suspended, or have been reported lost, or stolen; had tried to Book a Vehicle or Sign up. <br>");
            stringBuffer.append("<img src=\"cid:image1\" width=\"30%\" height=\"30%\" /><br>");
            stringBuffer.append("I request you to take immediate actions, and figure out the reason. <br>");
            stringBuffer.append("Thank you ! <br>");
            stringBuffer.append("</html>");

            emailSender.send(adminEmail,
                    stringBuffer.toString(),
                    "DMV suspended driver detected", imageData,"driver.png" );

            emailSender.send(dmvEmail,
                    stringBuffer.toString(),
                    "DMV suspended driver detected", imageData,"driver.png" );
        } catch (Exception e) {
            log.error("error in retrieving and casting image to a file: " + e.getMessage());
        }
    }
}
