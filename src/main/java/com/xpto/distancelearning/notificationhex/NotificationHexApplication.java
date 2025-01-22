package com.xpto.distancelearning.notificationhex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableEurekaClient // No more need to specify this annotation. It is included in the dependencies
public class NotificationHexApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationHexApplication.class, args);
    }

}
