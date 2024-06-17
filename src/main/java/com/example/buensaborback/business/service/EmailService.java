package com.example.buensaborback.business.service;


import com.example.buensaborback.config.email.EmailDto;
import jakarta.mail.MessagingException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface EmailService {
    public void enviarEmail(EmailDto emailDto, ByteArrayOutputStream pdfContent) throws MessagingException, IOException;
}
