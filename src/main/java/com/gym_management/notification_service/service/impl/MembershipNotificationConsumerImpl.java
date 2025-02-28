package com.gym_management.notification_service.service.impl;

import com.gym_management.notification_service.dto.MembershipEventDto;
import com.gym_management.notification_service.service.MembershipNotificationConsumer;
import com.gym_management.notification_service.service.MessageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MembershipNotificationConsumerImpl implements MembershipNotificationConsumer {

    private final MessageService messageService;

    public MembershipNotificationConsumerImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    public Mono<Void> handleMembershipEvent(MembershipEventDto event) {
        String userEmail = "receptor.email.java@gmail.com";
        String subject = "Membership update: " + event.action();
        String body = event.message();
        return messageService.sendNotification(userEmail, subject, body);
    }

    private String getEmailByUuid(UUID userId) {
        // TODO: Implement this method to dynamically retrieve emails
        return "user@example.com";
    }
}
