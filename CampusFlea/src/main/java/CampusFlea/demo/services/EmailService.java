package CampusFlea.demo.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {
    private static final String USERNAME = "handwritingpractice@gmail.com";
    private static final String PASSWORD = "knnkxykwtypwuazj";

    public static void sendEmail(String emailTo, String subject, String body) {
        // Create connection properties
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        // Create the authenticator
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };

        // Create the auth session
        Session session = Session.getInstance(prop, auth);

        try {
            // Create the new message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
