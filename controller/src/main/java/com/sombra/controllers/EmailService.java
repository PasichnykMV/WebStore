package com.sombra.controllers;

import com.sombra.model.User;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class EmailService {
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    private static EmailService emailService;

    protected EmailService() {
    }

    public static EmailService getInstance() {
        if (emailService == null) {
            emailService = new EmailService();
        }
        return emailService;
    }

    public void sendMessage(String reason, String subject, User user, String filePath){
        if (reason.equals("sign up")) {
            sendRegistrationMsg(subject, user);
        } else if (reason.equals("purchase")){
            try {
                sendEmailWithAttachments(subject, user, filePath);
            } catch (MessagingException e) {
                LOGGER.error("Exception in EmailService", e);
            }
        }
    }

    public void sendRegistrationMsg(String subject, User user){

        LOGGER.info("Entering to EmailService");

        String from = "ourtest220@gmail.com";
        final String username = "ourtest220";
        final String password = "easyeasy";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("mpasichnyk220@gmail.com"));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(getRegistrationMessageTemplate(user));
            Transport.send(message);
            LOGGER.info("Message send");
        } catch (MessagingException e) {
            LOGGER.error("Exception in " + this.getClass().getName(), e);
        }
    }

    private String getRegistrationMessageTemplate(User user) {
        StringBuilder message = new StringBuilder("Hello ");
        message.append(user.getName()).append(" ").append(user.getLastName()).append("!\n")
                .append("Congratulation, You have successfuly signed up at our web store and" +
                        " we hope for pleasant cooperation in future. \n")
                .append("Best regards, SombraWebStore.");
        return message.toString();
    }

    public static void sendEmailWithAttachments(String subject, User user, String filePath)
            throws AddressException, MessagingException {

        String from = "ourtest220@gmail.com";
        final String username = "ourtest220";
        final String password = "easyeasy";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(from));
        InternetAddress[] toAddresses = { new InternetAddress(user.getEmail()) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Your check");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();
        try {
            attachPart.attachFile(filePath);
        } catch (IOException ex) {
            LOGGER.error("Exception in EmailService ", ex);
        }
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
}