package com.gym_management.notification_service.service;

import com.gym_management.notification_service.dto.MembershipEventDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public interface MembershipNotificationConsumer {
    Function<Flux<MembershipEventDto>, Mono<Void>> processMembershipEvent();
}
