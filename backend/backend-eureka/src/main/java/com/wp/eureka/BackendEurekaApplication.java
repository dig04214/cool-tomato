package com.wp.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEurekaServer
public class BackendEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEurekaApplication.class, args);
    }

}
