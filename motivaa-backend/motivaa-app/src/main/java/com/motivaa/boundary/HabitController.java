package com.motivaa.boundary;

import com.motivaa.boundary.dto.CreateHabitRequest;
import com.motivaa.boundary.dto.CreateHabitResponse;
import com.motivaa.control.HabitCreationService;
import com.motivaa.entity.Habit;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/apis/habit")
@Log4j2
public class HabitController {
    HabitCreationService habitCreationService;

    HabitController(HabitCreationService habitCreationService){
        this.habitCreationService = habitCreationService;
    }

    @PostMapping("/createHabit")
    public ResponseEntity<CreateHabitResponse> createHabit(@Valid @RequestBody CreateHabitRequest createHabitRequest) {
        log.trace("Habit creation for habit started with details: {}", createHabitRequest);
        Habit habit = habitCreationService.createHabit(
                        createHabitRequest.getUserUuid(),
                        createHabitRequest.getName(),
                        createHabitRequest.getRecurringType(),
                        createHabitRequest.getRecurringDetails(),
                        createHabitRequest.getPriority(),
                        createHabitRequest.getColor());
        return ResponseEntity.ok(CreateHabitResponse.fromHabit(habit));
    }
}
