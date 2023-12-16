package org.comcom.notification.email.template;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class DefaultEmailEmailTemplateService implements EmailTemplateService {
    private final ITemplateEngine templateEngine;

    @Override
    public String getPasswordResetRequestEmailContent(String firstName, int resetKey, int expirationTimeInHours) {
        Context context = new Context();
        context.setVariable("resetKey", resetKey);
        context.setVariable("expirationTimeInHours", expirationTimeInHours);
        context.setVariable("firstName", firstName);

        return templateEngine.process("password-reset-request", context);
    }

    @Override
    public String getPasswordResetConfirmationEmailContent(String firstName) {
        Context context = new Context();
        context.setVariable("firstName", firstName);

        return templateEngine.process("password-reset-confirmation", context);
    }
}
