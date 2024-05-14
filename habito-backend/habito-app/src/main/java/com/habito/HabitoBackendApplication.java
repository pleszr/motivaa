package com.habito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:motivaa.properties")
public class HabitoBackendApplication{

    public static void main(String[] args) {
        SpringApplication.run(HabitoBackendApplication.class, args);
    }

}
