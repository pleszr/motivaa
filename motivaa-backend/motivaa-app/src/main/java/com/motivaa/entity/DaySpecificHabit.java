package com.motivaa.entity;

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

//    @Override
//    public String getRecurringTypeDetails() {
//        return String.join(";", listOfRecurringDays);
//    }
}
