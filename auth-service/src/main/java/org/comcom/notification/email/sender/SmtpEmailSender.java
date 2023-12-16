package org.comcom.notification.email.sender;

import lombok.AllArgsConstructor;
import org.comcom.dto.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service("smtp")
@AllArgsConstructor
public class SmtpEmailSender implements EmailSender {
    private final JavaMailSender javaMailSender;
    private final Logger logger = LoggerFactory.getLogger(SmtpEmailSender.class);

    @Override
    public void send(String recipient, Sender sender, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, StandardCharsets.UTF_8.name());
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setFrom(sender.email(), sender.name());

            logger.info(String.format("Sending email for user with email: %s", recipient));
            javaMailSender.send(mimeMessage);
            logger.info(String.format("Successfully sent email to user with email: %s", recipient));
        } catch (Exception e) {
            logger.info(String.format("Failed to send email to user with email: %s", recipient));
            e.printStackTrace();
        }
    }
}
