package com.xpto.distancelearning.notificationhex.adapters.outbound.persistence;

import com.xpto.distancelearning.notificationhex.adapters.outbound.persistence.entities.NotificationEntity;
import com.xpto.distancelearning.notificationhex.core.domain.NotificationDomain;
import com.xpto.distancelearning.notificationhex.core.domain.PageInfo;
import com.xpto.distancelearning.notificationhex.core.domain.enums.NotificationStatus;
import com.xpto.distancelearning.notificationhex.core.ports.NotificationPersistencePort;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class NotificationPersistencePortImpl implements NotificationPersistencePort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final ModelMapper modelMapper;

    public NotificationPersistencePortImpl(NotificationJpaRepository notificationJpaRepository, ModelMapper modelMapper) {
        this.notificationJpaRepository = notificationJpaRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotificationDomain save(NotificationDomain notificationDomain) {
        NotificationEntity notificationEntity = notificationJpaRepository.save(modelMapper.map(notificationDomain, NotificationEntity.class));
        return modelMapper.map(notificationEntity, NotificationDomain.class);
    }

    @Override
    public List<NotificationDomain> findAllByUserIdAndNotificationStatus(UUID userId, NotificationStatus notificationStatus, PageInfo pageInfo) {
        Pageable pageable = PageRequest.of(pageInfo.getPageNumber(), pageInfo.getPageSize());
        return notificationJpaRepository.findAllByUserIdAndNotificationStatus(userId, notificationStatus, pageable)
                .stream()
                .map(entity -> modelMapper.map(entity, NotificationDomain.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NotificationDomain> findByNotificationIdAndUserId(UUID notificationId, UUID userId) {
        Optional<NotificationEntity> notificationEntityOptional = notificationJpaRepository.findByNotificationIdAndUserId(notificationId, userId);
        if (notificationEntityOptional.isPresent()) {
            return Optional.of(modelMapper.map(notificationEntityOptional.get(), NotificationDomain.class));
        } else {
            return Optional.empty();
        }
    }
}