package com.habito;

import com.habito.boundary.HealthCheckController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = HealthCheckController.class)
@SpringBootApplication
public class HabitoBackendApplication{

    public static void main(String[] args) {
        SpringApplication.run(HabitoBackendApplication.class, args);
    }

}
