package com.xpto.distancelearning.notificationhex.adapters.inbound.controllers;

import com.xpto.distancelearning.notificationhex.adapters.dtos.NotificationDto;
import com.xpto.distancelearning.notificationhex.core.domain.NotificationDomain;
import com.xpto.distancelearning.notificationhex.core.domain.PageInfo;
import com.xpto.distancelearning.notificationhex.core.ports.NotificationServicePort;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificationController {

    private final NotificationServicePort notificationServicePort;

    public UserNotificationController(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<Page<NotificationDomain>> getAllNotificationsByUser(@PathVariable(value="userId") UUID userId,
                                                                              @PageableDefault(page = 0, size = 10, sort = "notificationId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                              Authentication authentication) { // authentication is the object that contains the current user's information (username, password, roles, etc.) that is accessing the system.
        PageInfo pageInfo = new PageInfo();
        BeanUtils.copyProperties(pageable, pageInfo);
        List<NotificationDomain> notificationDomainList = notificationServicePort.findAllNotificationsByUser(userId, pageInfo);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(notificationDomainList, pageable, notificationDomainList.size()));
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PutMapping("/users/{userId}/notifications/{notificationId}")
    public ResponseEntity<Object> updateNotification(@PathVariable(value="userId") UUID userId,
                                                     @PathVariable(value="notificationId") UUID notificationId,
                                                     @RequestBody @Valid NotificationDto notificationDto) {
        Optional<NotificationDomain> notificationModelOptional = notificationServicePort.findByNotificationIdAndUserId(notificationId, userId);
        if(notificationModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found!");
        }
        notificationModelOptional.get().setNotificationStatus(notificationDto.getNotificationStatus());
        notificationServicePort.saveNotification(notificationModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(notificationModelOptional.get());
    }
}
