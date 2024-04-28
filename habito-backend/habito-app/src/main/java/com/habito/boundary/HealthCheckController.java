package com.habito.boundary;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apis")
@Log4j2
public class HealthCheckController {
    @GetMapping("/healthcheck")
    public ResponseEntity<Map<String, String>> healthChecker(){
        log.trace("healthcheck successful");
        Map<String, String> response = new HashMap<>();
        response.put("message", "cica2: healthcheck successful");
        return ResponseEntity.ok(response);
    }
}
