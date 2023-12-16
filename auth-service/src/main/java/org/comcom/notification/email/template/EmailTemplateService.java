package org.comcom.notification.email.template;

public interface EmailTemplateService {
    String getPasswordResetRequestEmailContent(String firstName, int resetKey, int expirationTimeInHours);

    String getPasswordResetConfirmationEmailContent(String firstName);
}
