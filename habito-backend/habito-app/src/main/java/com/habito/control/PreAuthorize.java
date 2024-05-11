package com.habito.control;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is public, it means available for everyone";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Need to configure this..
    public String adminOnly() {
        return "Only admin can see this.";
    }
}