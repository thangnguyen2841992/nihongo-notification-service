package com.regain.notificationservice.service;

import com.regain.notificationservice.model.MessageDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements IEmailService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(MessageDTO messageDTO) {
        try {
            logger.info("START... Sending email");
            MimeMessage message = javaMailSender.createMimeMessage();
//            Điều này chỉ định sử dụng charset UTF-8, giúp đảm bảo rằng tất cả các ký tự trong nội dung email đều được mã hóa đúng cách, đặc biệt là khi bạn làm việc với các ký tự đặc biệt hoặc ngôn ngữ không phải tiếng Anh.
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
//            load template email with content
            Context context = new Context();
            context.setVariable("name", messageDTO.getToName());
            context.setVariable("content", messageDTO.getContent());
            context.setVariable("username", messageDTO.getUsername());
            String url = "http://localhost:3000/active/" + messageDTO.getUsername();
            context.setVariable("url", url);
            String html = templateEngine.process("welcome-email", context);

//            send Email
            helper.setTo(messageDTO.getTo());
            helper.setText(html, true);
            helper.setSubject(messageDTO.getSubject());
            helper.setFrom(messageDTO.getFrom());
            javaMailSender.send(message);
            logger.info("End... Email sent success");
        }catch (MessagingException e) {
            logger.error("Email sent with error: " + e.getMessage());
        }
    }
}
