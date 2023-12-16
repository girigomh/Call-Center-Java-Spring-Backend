package org.comcom.notification;

import org.springframework.scheduling.annotation.Async;

public interface NotificationService {
    @Async
    void sendPasswordResetRequestEmail(String email, String firstName, int resetKey, int expirationTimeInHours);

    @Async
    void sendPasswordResetConfirmationEmail(String email, String firstName);
}
