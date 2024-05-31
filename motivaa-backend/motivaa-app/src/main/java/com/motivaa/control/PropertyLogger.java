package com.motivaa.control;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PropertyLogger {


    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databasePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @PostConstruct
    public void run() {
        log.info("Database URL: {}", databaseUrl);
        log.info("Database User: {}", databaseUser);
        log.info("Database Password: {}", databasePassword);
        log.info("Driver Class Name: {}", driverClassName);
    }
}