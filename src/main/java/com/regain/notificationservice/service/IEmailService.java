package com.regain.notificationservice.service;

import com.regain.notificationservice.model.MessageDTO;

public interface IEmailService {
    void sendEmail(MessageDTO messageDTO);
}
