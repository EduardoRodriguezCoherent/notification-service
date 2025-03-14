package com.gym_management.notification_service.service;

import com.gym_management.notification_service.domain.MembershipNotificationDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MembershipNotificationDetailsService {
    Flux<MembershipNotificationDetails> getAll();

    Mono<MembershipNotificationDetails> getById(String id);

    Mono<MembershipNotificationDetails> save(MembershipNotificationDetails notificationDetails);

}