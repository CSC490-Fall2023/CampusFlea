package CampusFlea.demo.services;

public class NotificationService {
    public static void sendChatNotification(int fromId, int toId, String message) {
        String fromUsername = AccountService.getUsername(fromId);
        String toUsername = AccountService.getUsername(toId);
        String toEmail = AccountService.getEmail(toId);

        String subject = "You received a new message from " + fromUsername;
        String body = "<p>Hello " + toUsername + ",</p>";
        body += "<p>" + fromUsername + " sent you a message:</p>";
        body += "<p>" + message + "</p>";

        EmailService.sendEmail(toEmail, subject, body);

    }
}
