package com.motivaa.boundary.dto;

import com.motivaa.boundary.validators.ValidColor;
import com.motivaa.boundary.validators.ValidListOfRecurringDays;
import com.motivaa.boundary.validators.ValidRecurringType;
import com.motivaa.control.utility.Constants;
import com.motivaa.control.utility.PossibleColors;
import com.motivaa.control.utility.PossibleDays;
import com.motivaa.control.utility.PossibleRecurringTypes;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateHabitRequest {
    @NotBlank(message = "User UUID is mandatory")
    @Pattern(
            regexp = Constants.UUID_REGEX,
            message = "Invalid User UUID format.")
    @Schema(
            description = "UUID of the user",
            example = "9e86bb88-1b4e-4097-b966-2f882f717b41",
            pattern = Constants.UUID_REGEX)
    String userUuid;

    @NotBlank(message = "Name is mandatory")
    @Pattern(
            regexp = Constants.NAME_REGEX,
            message = "Invalid name format.")
    @Schema(
            description = "Name of the habit",
            example = "Running",
            pattern = Constants.NAME_REGEX)
    String name;

    @NotBlank(message = "Recurring type is mandatory")
    @Schema(
            description = "Recurring type of the habit",
            example = "specific_day",
            implementation = PossibleRecurringTypes.class)
    @ValidRecurringType
    String recurringType;

    @ValidListOfRecurringDays
    @Schema(
            description = "Recurring days of the habit. Expects a list of days separated by semi-colon (;). Mandatory if recurring type is specific_day",
            example = "monday",
            implementation = PossibleDays.class)
    String listOfRecurringDays;

    @Min(value = 1, message = "Number of occasions in a week must be between 1 and 7")
    @Max(value = 7, message = "Number of occasions in a week must be between 1 and 7")
    @Schema(
            description = "Number of occasions in a week. Mandatory if recurring type is non_specific_day",
            minimum = "1",
            maximum = "7")
    Integer numberOfOccasionsInWeek;

    @Min(value = 1, message = "Priority must be between 1 and 5")
    @Max(value = 5, message = "Priority must be between 1 and 5")
    @Schema(
            description = "Priority of the habit",
            example = "3",
            minimum = "1",
            maximum = "5")
    @NotNull(message = "Priority is mandatory")
    Integer priority;

    @Schema (
            description = "Color of the habit",
            example = "red",
            implementation = PossibleColors.class)
    @ValidColor
    @NotBlank(message = "Color is mandatory")
    String color;


}
