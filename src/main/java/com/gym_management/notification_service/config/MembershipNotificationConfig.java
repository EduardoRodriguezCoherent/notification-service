package com.gym_management.notification_service.config;

import com.gym_management.notification_service.dto.MembershipEventDto;
import com.gym_management.notification_service.service.MembershipNotificationConsumer;
import com.gym_management.notification_service.service.impl.MembershipNotificationConsumerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class MembershipNotificationConfig {

    private final MembershipNotificationConsumer notificationConsumer;

    public MembershipNotificationConfig(MembershipNotificationConsumer notificationConsumer) {
        this.notificationConsumer = notificationConsumer;
    }

    @Bean
    public Function<Flux<MembershipEventDto>, Mono<Void>> processMembershipEvent() {
        return eventFlux -> eventFlux
                .doOnNext(event -> System.out.println("Received event: " + event))
                .flatMap(notificationConsumer::handleMembershipEvent)
                .then();
    }
}