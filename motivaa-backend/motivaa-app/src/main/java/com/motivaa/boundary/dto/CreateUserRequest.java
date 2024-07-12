package com.motivaa.boundary.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class CreateUserRequest {
    @NotBlank(message = "Email is mandatory")
    String email;
    @NotBlank(message = "First name is mandatory")
    String firstName;
    @NotBlank(message = "Last name is mandatory")
    String lastName;
}
