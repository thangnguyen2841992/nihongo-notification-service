package com.regain.notificationservice.controller;

import com.regain.notificationservice.model.MessageDTO;
import com.regain.notificationservice.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class NotificationController {
    @Autowired
    private IEmailService emailService;

    @PostMapping("/sendNotificationEmail.do")
    public void sendNotificationEmail(@RequestBody MessageDTO messageDTO) {
        emailService.sendEmail(messageDTO);
    }
}
