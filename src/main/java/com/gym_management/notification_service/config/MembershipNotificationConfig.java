package com.gym_management.notification_service.config;

import com.gym_management.notification_service.dto.MembershipEventDto;
import com.gym_management.notification_service.service.MembershipNotificationConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class MembershipNotificationConfig {

    private static final Logger log = LoggerFactory.getLogger(MembershipNotificationConfig.class);

    private final MembershipNotificationConsumer notificationConsumer;

    public MembershipNotificationConfig(MembershipNotificationConsumer notificationConsumer) {
        this.notificationConsumer = notificationConsumer;
    }

    @Bean
    public Function<Flux<MembershipEventDto>, Mono<Void>> processMembershipEvent() {
        return eventFlux -> eventFlux
                .doOnNext(event -> log.info("Received event: {}", event))
                .flatMap(notificationConsumer::handleMembershipEvent)
                .onErrorContinue((error, event) -> log.error("Error processing event: {}", error.getMessage()))
                .then();
    }
}