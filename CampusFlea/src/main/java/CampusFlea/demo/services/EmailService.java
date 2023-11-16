package CampusFlea.demo.services;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailService {
    private static final String EMAIL = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);

            System.out.printf("Sent email to %s\n", emailTo);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}
