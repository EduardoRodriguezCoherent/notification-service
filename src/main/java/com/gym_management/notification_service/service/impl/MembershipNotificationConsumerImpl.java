package com.gym_management.notification_service.service.impl;

import com.gym_management.notification_service.domain.MembershipNotificationDetails;
import com.gym_management.notification_service.dto.MembershipEventDto;
import com.gym_management.notification_service.repository.MembershipNotificationDetailsRepository;
import com.gym_management.notification_service.service.MembershipNotificationConsumer;
import com.gym_management.notification_service.service.MessageService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class MembershipNotificationConsumerImpl implements MembershipNotificationConsumer {

    private final MessageService messageService;
    private final MembershipNotificationDetailsRepository notificationRepository;

    public MembershipNotificationConsumerImpl(MessageService messageService, MembershipNotificationDetailsRepository notificationRepository) {
        this.messageService = messageService;
        this.notificationRepository = notificationRepository;
    }

    public Mono<Void> handleMembershipEvent(MembershipEventDto event) {

        MembershipNotificationDetails notification = new MembershipNotificationDetails();
        String userEmail = "receptor.email.java@gmail.com";
        String subject = "Membership update: " + event.action();
        String body = event.message();

        notification.setMembershipUuid(event.uuid());
        notification.setAction(event.action());
        notification.setType(event.type());
        notification.setEmail(userEmail);
        notification.setMessage(event.message());
        notification.setReceptionDate(LocalDate.now());
        notification.setSent(false);

        return notificationRepository.save(notification)
                .flatMap(savedNotification ->
                        messageService.sendNotification(userEmail, subject, body)
                                .then(notificationRepository.save(updateSentStatus(savedNotification, true)))
                )
                .onErrorResume(error ->
                        notificationRepository.save(updateSentStatus(notification, false))
                )
                .then();
    }

    private String getEmailByUuid(UUID userId) {
        // TODO: Implement this method to dynamically retrieve emails
        return "user@example.com";
    }

    private MembershipNotificationDetails updateSentStatus(MembershipNotificationDetails notification, boolean status) {
        notification.setSent(status);
        notification.setSentDate(status ? LocalDate.now() : null);
        return notification;
    }
}
