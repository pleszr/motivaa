package com.motivaa.boundary;

import com.motivaa.control.MotivaaService;
import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apis")
@Log4j2
public class HealthCheckController {
    MotivaaService motivaaService;

    HealthCheckController(MotivaaService motivaaService){
        this.motivaaService = motivaaService;
    }


    @GetMapping("/healthcheck")
    public ResponseEntity<Map<String, String>> healthChecker(){
        log.trace("healthcheck successful");
        Map<String, String> response = new HashMap<>();
        response.put("healthStatus", "ok");
        response.put("customMessage", "cica");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createRandomUser")
    public ResponseEntity<User> playground(){
        User user = motivaaService.createRandomUser();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/searchUserByFirstName")
    public ResponseEntity<List<User>> searchUserByFirstName(@RequestParam("firstName") String firstName) throws java.io.IOException{
        List<User> users = motivaaService.searchUserByFirstName(firstName);
        return ResponseEntity.ok(users);
    }


}
