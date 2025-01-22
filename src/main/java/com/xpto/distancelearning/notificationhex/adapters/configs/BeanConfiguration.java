package com.xpto.distancelearning.notificationhex.adapters.configs;

import com.xpto.distancelearning.notificationhex.NotificationHexApplication;
import com.xpto.distancelearning.notificationhex.core.ports.NotificationPersistencePort;
import com.xpto.distancelearning.notificationhex.core.services.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationHexApplication.class)
public class BeanConfiguration {

    @Bean
    public NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort persistence) {
        return new NotificationServicePortImpl(persistence);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}