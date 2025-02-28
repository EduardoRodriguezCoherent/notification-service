package com.gym_management.notification_service.dto;

import com.gym_management.notification_service.domain.MembershipAction;
import com.gym_management.notification_service.domain.MembershipType;

import java.util.UUID;

public record MembershipEventDto(
        UUID uuid,
        MembershipAction action,
        MembershipType type,
        String message) {
}
