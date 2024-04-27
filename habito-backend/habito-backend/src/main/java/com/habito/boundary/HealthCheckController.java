package com.habito.boundary;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/apis")
@Log4j2
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public ResponseEntity<String> healthChecker(){
        log.trace("healthcheck successful");
        return ResponseEntity.ok("cica2: healthcheck successful");
    }
}
