package com.springapp.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailingService {
    private final String SYSTEM_EMAIL_USER_NAME = "alinagilbart28@gmail.com";
    private final String SYSTEM_EMAIL_PASSWORD = "alinagilbert28";
    private Properties emailProperties;

    public void setEmailProperties(Properties emailProperties) {
        this.emailProperties = emailProperties;
    }

    public boolean email(String recipient,  String content, String subject){

        Session session = Session.getInstance(emailProperties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SYSTEM_EMAIL_USER_NAME, SYSTEM_EMAIL_PASSWORD);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SYSTEM_EMAIL_USER_NAME));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setContent(content, "text/html");

            Transport.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
