package com.gym_management.notification_service.repository;

import com.gym_management.notification_service.domain.MembershipNotificationDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipNotificationDetailsRepository extends ReactiveMongoRepository<MembershipNotificationDetails, String> {
}
