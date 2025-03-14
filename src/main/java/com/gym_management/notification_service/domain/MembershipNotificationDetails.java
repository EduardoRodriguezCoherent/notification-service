package com.gym_management.notification_service.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "membership_notifications")
@AllArgsConstructor
@NoArgsConstructor
public class MembershipNotificationDetails {

    @Id
    private String id;

    @Field(targetType = FieldType.STRING)
    private UUID membershipUuid;

    private MembershipAction action;

    private MembershipType type;

    private String message;

    private String email;

    private LocalDate receptionDate;

    private LocalDate sentDate;

    private boolean isSent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getMembershipUuid() {
        return membershipUuid;
    }

    public void setMembershipUuid(UUID membershipUuid) {
        this.membershipUuid = membershipUuid;
    }

    public MembershipAction getAction() {
        return action;
    }

    public void setAction(MembershipAction action) {
        this.action = action;
    }

    public MembershipType getType() {
        return type;
    }

    public void setType(MembershipType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(LocalDate receptionDate) {
        this.receptionDate = receptionDate;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }
}