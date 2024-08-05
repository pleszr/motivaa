package com.motivaa.boundary.dto;

import com.motivaa.entity.Habit;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class CreateHabitResponse {
    UUID uuid;
    String userUuid;
    String name;
    String recurringType;
    List<String> recurringDetails;
    Integer priority;
    String color;

    private CreateHabitResponse(Habit habit) {
        this.uuid = habit.getUuid();
        this.userUuid = habit.getUserUuid();
        this.name = habit.getName();
        this.recurringType = habit.getRecurringType();
        this.recurringDetails = habit.getRecurringTypeDetails();
        this.priority = habit.getPriority();
        this.color = habit.getColor();
    }

    public static CreateHabitResponse fromHabit (Habit habit) {
        return new CreateHabitResponse(habit);
    }
}
