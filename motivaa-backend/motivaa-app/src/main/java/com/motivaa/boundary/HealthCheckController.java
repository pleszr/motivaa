package com.motivaa.boundary;

import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        response.put("healthStatus", "ok");
        response.put("customMessage", "cica");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/playground")
    public ResponseEntity<String> playground(){
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/authTest")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Only authenticated users can see this.");
    }
}
