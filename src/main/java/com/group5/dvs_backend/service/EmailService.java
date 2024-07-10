package com.group5.dvs_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.group5.dvs_backend.entity.EmailDetail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMailTemplate(EmailDetail emailDetail, String templateName) {

        try {
            Context context = new Context();
            context.setVariable("username", emailDetail.getName());
            context.setVariable("email", emailDetail.getRecipient());
            // context.setVariable("registrationDate",java.time.Clock.systemUTC().instant());

            // template name la ten template trong folder resources/templates
            String text = templateEngine.process(templateName, context);

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom("tridvse173191@fpt.edu.vn");
            mimeMessageHelper.setTo(emailDetail.getRecipient());
            mimeMessageHelper.setText(text, true);
            mimeMessageHelper.setSubject(emailDetail.getSubject());

            // if (emailDetail.getAttachment() != null) {
            // mimeMessageHelper.addAttachment(emailDetail.getAttachment().getFilename(),
            // emailDetail.getAttachment());
            // }

            javaMailSender.send(mimeMessage);
        } catch (MessagingException messagingException) {
            messagingException.printStackTrace();
        }
    }

}