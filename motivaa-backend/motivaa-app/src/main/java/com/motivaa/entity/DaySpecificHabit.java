package com.motivaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class DaySpecificHabit extends Habit {
    private List<String> listOfRecurringDays;

    public DaySpecificHabit(String userUuid,
                               String name,
                               String recurringType,
                               List<String> listOfRecurringDays,
                               Integer priority,
                               String color) {
        super(userUuid, name, recurringType, priority, color);
        this.listOfRecurringDays = listOfRecurringDays;
    }

    @JsonIgnore
    @Override
    public List<String> getRecurringTypeDetails() {
        return listOfRecurringDays;
    }
}
