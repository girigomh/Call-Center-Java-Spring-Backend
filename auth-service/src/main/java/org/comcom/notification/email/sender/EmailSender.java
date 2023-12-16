package org.comcom.notification.email.sender;

import org.comcom.dto.Sender;

public interface EmailSender {
    void send(String recipient, Sender sender, String subject, String body);
}
