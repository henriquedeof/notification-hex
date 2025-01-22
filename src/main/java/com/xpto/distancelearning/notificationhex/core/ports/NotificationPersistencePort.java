package com.xpto.distancelearning.notificationhex.core.ports;

import com.xpto.distancelearning.notificationhex.core.domain.NotificationDomain;
import com.xpto.distancelearning.notificationhex.core.domain.PageInfo;
import com.xpto.distancelearning.notificationhex.core.domain.enums.NotificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationPersistencePort {

    NotificationDomain save(NotificationDomain notificationDomain);

    List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo);

    Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId);

}