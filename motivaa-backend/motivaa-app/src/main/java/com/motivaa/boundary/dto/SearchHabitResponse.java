package com.motivaa.boundary.dto;

import com.motivaa.entity.Habit;
import lombok.Value;

import java.util.UUID;

@Value
public class SearchHabitResponse {
    UUID uuid;
    String userUuid;
    String name;
    String recurringType;
    String recurringDetails;
    Integer priority;
    String color;

    private SearchHabitResponse(Habit habit) {
        this.uuid = habit.getUuid();
        this.userUuid = habit.getUserUuid();
        this.name = habit.getName();
        this.recurringType = habit.getRecurringType();
        this.recurringDetails = habit.getRecurringTypeDetails();
        this.priority = habit.getPriority();
        this.color = habit.getColor();
    }

    public static SearchHabitResponse fromHabit (Habit habit) {
        return new SearchHabitResponse(habit);
    }
}
