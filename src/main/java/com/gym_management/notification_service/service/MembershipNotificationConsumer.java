package com.gym_management.notification_service.service;

import com.gym_management.notification_service.dto.MembershipEventDto;
import reactor.core.publisher.Mono;

public interface MembershipNotificationConsumer {
    Mono<Void> handleMembershipEvent(MembershipEventDto event);
}
