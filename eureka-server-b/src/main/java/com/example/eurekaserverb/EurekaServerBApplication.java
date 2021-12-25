package com.example.eurekaserverb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServerBApplication {
    private final Logger logger = LoggerFactory.getLogger(EurekaServerBApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerBApplication.class, args);
    }

}
