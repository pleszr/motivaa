package com.motivaa.boundary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHabitRequest {
    @NotBlank(message = "User UUID is mandatory")
    String userUuid;
    @NotBlank(message = "Name is mandatory")
    String name;
    @NotBlank(message = "Recurring type is mandatory")
    String recurringType;
    @NotBlank(message = "Recurring details is mandatory")
    String recurringDetails;
    @NotNull(message = "Priority is mandatory")
    Integer priority;
    @NotBlank(message = "Color is mandatory")
    String color;


}
