package com.xpto.distancelearning.notificationhex.adapters.inbound.consumers;

import com.xpto.distancelearning.notificationhex.core.domain.NotificationDomain;
import com.xpto.distancelearning.notificationhex.core.domain.enums.NotificationStatus;
import com.xpto.distancelearning.notificationhex.core.ports.NotificationServicePort;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class NotificationConsumer {

    private final NotificationServicePort notificationServicePort;

    public NotificationConsumer(NotificationServicePort notificationServicePort) {
        this.notificationServicePort = notificationServicePort;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${dl.broker.queue.notificationCommandQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${dl.broker.exchange.notificationCommandExchange}", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = "${dl.broker.key.notificationCommandKey}")
    )
    public void listen(@Payload com.xpto.distancelearning.notificationhex.adapters.dtos.NotificationCommandDto notificationCommandDto) {
        var notificationModel = new NotificationDomain();
        BeanUtils.copyProperties(notificationCommandDto, notificationModel);
        notificationModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        notificationModel.setNotificationStatus(NotificationStatus.CREATED);
        notificationServicePort.saveNotification(notificationModel);
    }
}