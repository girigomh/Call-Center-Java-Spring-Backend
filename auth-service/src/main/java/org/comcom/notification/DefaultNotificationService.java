package org.comcom.notification;

import lombok.AllArgsConstructor;
import org.comcom.dto.Sender;
import org.comcom.notification.email.sender.EmailSender;
import org.comcom.notification.email.template.EmailTemplateService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DefaultNotificationService implements NotificationService {
    private EmailTemplateService emailTemplateService;
    private EmailSender emailSender;

    @Override
    public void sendPasswordResetRequestEmail(String email, String firstName, int resetKey, int expirationTimeInHours) {
        String body = emailTemplateService.getPasswordResetRequestEmailContent(firstName, resetKey, expirationTimeInHours);
        Sender sender = new Sender("no-reply@comcom.com", "ComCom");
        String subject = "Request to reset your password";

        sendEmail(email, sender, subject, body);
    }

    @Override
    public void sendPasswordResetConfirmationEmail(String email, String firstName) {
        String body = emailTemplateService.getPasswordResetConfirmationEmailContent(firstName);
        Sender sender = new Sender("no-reply@comcom.com", "ComCom");
        String subject = "You have reset your password";

        sendEmail(email, sender, subject, body);
    }

    private void sendEmail(String recipient, Sender sender, String subject, String body) {
        emailSender.send(recipient, sender, subject, body);
    }
}
