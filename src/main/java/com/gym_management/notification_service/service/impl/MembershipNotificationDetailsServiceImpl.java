package com.gym_management.notification_service.service.impl;

import com.gym_management.notification_service.domain.MembershipNotificationDetails;
import com.gym_management.notification_service.repository.MembershipNotificationDetailsRepository;
import com.gym_management.notification_service.service.MembershipNotificationDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MembershipNotificationDetailsServiceImpl implements MembershipNotificationDetailsService {

    private final MembershipNotificationDetailsRepository notificationRepository;

    public MembershipNotificationDetailsServiceImpl(MembershipNotificationDetailsRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Flux<MembershipNotificationDetails> getAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Mono<MembershipNotificationDetails> getById(String id) {
        return notificationRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification details with id: " + id + " not found.")));
    }

    @Override
    public Mono<MembershipNotificationDetails> save(MembershipNotificationDetails notificationDetails) {
        return notificationRepository.save(notificationDetails);
    }
}