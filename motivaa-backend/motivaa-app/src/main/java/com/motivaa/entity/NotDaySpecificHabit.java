package com.motivaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
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
    @JsonIgnore
    @Override
    public String getRecurringTypeDetails() {
        return numberOfOccasionsInWeek.toString();
    }


}
