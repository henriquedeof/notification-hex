package com.xpto.distancelearning.notificationhex.adapters.dtos;


import com.xpto.distancelearning.notificationhex.core.domain.enums.NotificationStatus;
import jakarta.validation.constraints.NotNull;

public class NotificationDto {

    @NotNull
    private NotificationStatus notificationStatus;

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }
}