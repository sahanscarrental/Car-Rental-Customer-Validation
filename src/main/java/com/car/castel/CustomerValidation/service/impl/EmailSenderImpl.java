package com.car.castel.CustomerValidation.service.impl;

import com.car.castel.CustomerValidation.service.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;


@Service
public class EmailSenderImpl  implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailAuthor;

    @Override
    public void send(String to, String body, String subject, byte[] attachment, String imageNameWithExtension) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            mimeMessage.setFrom(new InternetAddress(emailAuthor, "Banger & Co"));

            // Set To: header field of the header.
            mimeMessage.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            mimeMessage.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart textPart = new MimeBodyPart();
            String htmlText = body;
            textPart.setContent(htmlText, "text/html");

            // add it
            multipart.addBodyPart(textPart);

            if (attachment != null){
                try {
                    // set the image as an inline image
                    BodyPart imagePart = new MimeBodyPart();
                    imagePart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachment, "image/png")));
                    imagePart.setHeader("Content-ID", "<image1>");
                    imagePart.setFileName(imageNameWithExtension);
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                    multipart.addBodyPart(imagePart);
                } catch (Exception e) {
                    LOGGER.error("failed to attach file", e.getMessage());
                }
            }

            // put everything together
            mimeMessage.setContent(multipart);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
    }
}
