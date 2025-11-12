package com.events.eventsbooking.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName template,
            String activationCode,
            String confirmationUrl,
            String subject
    ) throws MessagingException {
        String templateName;

        if (template == null) {
            templateName = "confirm-email";
        } else {
            templateName = template.getName();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("activation_code", activationCode);
        model.put("confirmationUrl", confirmationUrl);

        Context context = new Context();
        context.setVariables(model);

        helper.setFrom("contact@TechHome.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String body = templateEngine.process(templateName, context);
        helper.setText(body, true);

        mailSender.send(mimeMessage);
    }
}
