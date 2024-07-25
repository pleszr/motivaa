package com.motivaa.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotDaySpecificHabit extends Habit {
    private Integer numberOfOccasionsInWeek;

    public NotDaySpecificHabit(String userUuid,
                               String name,
                               String recurringType,
                               Integer numberOfOccasionsInWeek,
                               Integer priority,
                               String color) {
        super(userUuid, name, recurringType, priority, color);
        this.numberOfOccasionsInWeek = numberOfOccasionsInWeek;
    }
//    @Override
//    public String getRecurringTypeDetails() {
//        return numberOfOccasionsInWeek.toString();
//    }


}
