package com.motivaa.boundary;

import com.motivaa.control.UserFinder;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apis")
@Log4j2
public class HealthCheckController {
    UserFinder userFinder;

    HealthCheckController(UserFinder userFinder){
        this.userFinder = userFinder;
    }
    @GetMapping("/healthcheck")
    public ResponseEntity<Map<String, String>> healthChecker(){
        log.trace("healthcheck successful");
        Map<String, String> response = new HashMap<>();
        response.put("healthStatus", "ok");
        response.put("customMessage", "cica");
        return ResponseEntity.ok(response);
    }
}
