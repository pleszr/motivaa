package com.motivaa.boundary.dto;

import com.motivaa.boundary.validators.ValidColor;
import com.motivaa.boundary.validators.ValidListOfRecurringDays;
import com.motivaa.boundary.validators.ValidRecurringType;
import com.motivaa.control.utility.Constants;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.control.utility.PossibleColors;
import com.motivaa.control.utility.PossibleDays;
import com.motivaa.control.utility.PossibleRecurringTypes;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateHabitRequest {
    @NotBlank(message = MessageBundle.MISSING_UUID_ERROR_MESSAGE)
    @Pattern(
            regexp = Constants.UUID_REGEX,
            message = MessageBundle.INVALID_UUID_FORMAT_ERROR_MESSAGE)
    @Schema(
            description = "UUID of the user",
            example = "9e86bb88-1b4e-4097-b966-2f882f717b41",
            pattern = Constants.UUID_REGEX)
    String userUuid;

    @NotBlank(message = MessageBundle.MISSING_NAME_ERROR_MESSAGE)
    @Pattern(
            regexp = Constants.NAME_REGEX,
            message = MessageBundle.INVALID_NAME_FORMAT_ERROR_MESSAGE)
    @Schema(
            description = "Name of the habit",
            example = "Running",
            pattern = Constants.NAME_REGEX)
    String name;

    @Schema(
            description = "Recurring type of the habit",
            example = "SPECIFIC_DAY",
            implementation = PossibleRecurringTypes.class)
    @ValidRecurringType
    String recurringType;

    @ValidListOfRecurringDays
    @ArraySchema(
            schema = @Schema(
            description = "Recurring days of the habit. Expects an array of days. Mandatory if recurring type is specific_day",
            implementation = PossibleDays.class))
    List<String> listOfRecurringDays;

    @Min(value = 1, message = MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE)
    @Max(value = 7, message = MessageBundle.INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE)
    @Schema(
            description = "Number of occasions in a week. Mandatory if recurring type is non_specific_day",
            minimum = "1",
            maximum = "7")
    Integer numberOfOccasionsInWeek;

    @Min(value = 1, message = MessageBundle.INVALID_PRIORITY_ERROR_MESSAGE)
    @Max(value = 5, message = MessageBundle.INVALID_PRIORITY_ERROR_MESSAGE)
    @Schema(
            description = "Priority of the habit",
            example = "3",
            minimum = "1",
            maximum = "5")
    @NotNull(message = MessageBundle.MISSING_PRIORITY_ERROR_MESSAGE)
    Integer priority;

    @Schema (
            requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Color of the habit",
            example = "RED",
            implementation = PossibleColors.class)
    @ValidColor
    String color;


}
