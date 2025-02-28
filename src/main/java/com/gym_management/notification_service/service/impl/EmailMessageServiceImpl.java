package com.gym_management.notification_service.service.impl;

import com.gym_management.notification_service.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailMessageServiceImpl implements MessageService {

    private final JavaMailSender javaMailSender;

    public EmailMessageServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Mono<Void> sendNotification(String to, String subject, String body) {
        return Mono.fromRunnable(()-> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            System.out.println("Email sent to: " + to);
        }).then();
    }
}
