package com.motivaa.boundary.dto;

import com.motivaa.entity.Habit;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateHabitResponse {
    UUID uuid;
    String userUuid;
    String name;
    String recurringType;
    String recurringDetails;
    Integer priority;
    String color;

    private CreateHabitResponse(Habit habit) {
        this.uuid = habit.getUuid();
        this.userUuid = habit.getUserUuid();
        this.name = habit.getName();
        this.recurringType = habit.getRecurringType();
        this.recurringDetails = habit.getRecurringDetails();
        this.priority = habit.getPriority();
        this.color = habit.getColor();
    }

    public static CreateHabitResponse fromHabit (Habit habit) {
        return new CreateHabitResponse(habit);
    }
}
