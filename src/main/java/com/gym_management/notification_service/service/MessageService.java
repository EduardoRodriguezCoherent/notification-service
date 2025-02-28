package com.gym_management.notification_service.service;

import reactor.core.publisher.Mono;

public interface MessageService {
    Mono<Void> sendNotification(String to, String subject, String body);
}
