package com.motivaa.control.monitoring;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PrintSwaggerUriPostStartup {
    @Value("${server.port}")
    int port;

    @PostConstruct
    public void printSwaggerUri() {
        log.info("Swagger UI: http://localhost:{}/swagger-ui.html",port);
    }
}
