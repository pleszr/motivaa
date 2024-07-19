package com.motivaa.boundary;

import com.motivaa.boundary.dto.CreateHabitRequest;
import com.motivaa.boundary.dto.CreateHabitResponse;
import com.motivaa.control.HabitCreationService;
import com.motivaa.entity.Habit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/habit-apis")
@Tag(name = "Habit API", description = "Endpoint to expose habit operations")
@Log4j2
public class HabitController {
    HabitCreationService habitCreationService;

    HabitController(HabitCreationService habitCreationService){
        this.habitCreationService = habitCreationService;
    }

    @Operation(summary = "Create a habit")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Habit created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateHabitResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found for the given userUuid",
                    content = @Content),
    })
    @PostMapping("/habits")
    public ResponseEntity<CreateHabitResponse> createHabit(@Valid @RequestBody CreateHabitRequest createHabitRequest) {
        log.trace("Habit creation for habit started with details: {}", createHabitRequest);
        Habit habit = habitCreationService.createHabit(
                        createHabitRequest.getUserUuid(),
                        createHabitRequest.getName(),
                        createHabitRequest.getRecurringType(),
                        createHabitRequest.getRecurringTypeDetails(),
                        createHabitRequest.getPriority(),
                        createHabitRequest.getColor());
        log.info("Habit creation for habit finished with details: {}", habit);
        return ResponseEntity.ok(CreateHabitResponse.fromHabit(habit));
    }
}
