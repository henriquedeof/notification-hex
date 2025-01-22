package com.xpto.distancelearning.notificationhex.core.services;

import com.xpto.distancelearning.notificationhex.core.domain.NotificationDomain;
import com.xpto.distancelearning.notificationhex.core.domain.PageInfo;
import com.xpto.distancelearning.notificationhex.core.domain.enums.NotificationStatus;
import com.xpto.distancelearning.notificationhex.core.ports.NotificationPersistencePort;
import com.xpto.distancelearning.notificationhex.core.ports.NotificationServicePort;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificationServicePortImpl implements NotificationServicePort {

    private final NotificationPersistencePort notificationPersistencePort;

    public NotificationServicePortImpl(NotificationPersistencePort notificationPersistencePort) {
        this.notificationPersistencePort = notificationPersistencePort;
    }

    @Override
    public NotificationDomain saveNotification(NotificationDomain NotificationDomain) {
        return notificationPersistencePort.save(NotificationDomain);
    }

    @Override
    public List<NotificationDomain> findAllNotificationsByUser(UUID userId, PageInfo pageInfo) {
        return notificationPersistencePort.findAllByUserIdAndNotificationStatus(userId, NotificationStatus.CREATED, pageInfo);
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        return notificationPersistencePort.findByNotificationIdAndUserId(notificationId, userId);
    }
}