package com.motivaa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@NoArgsConstructor
@Data
public abstract  class Habit {
    UUID uuid;
    String userUuid;
    String name;
    String recurringType;
    Integer priority;
    String color;

    protected Habit(String userUuid,
                 String name,
                 String recurringType,
                 Integer priority,
                 String color) {
        this.uuid = UUID.randomUUID();
        this.userUuid = userUuid;
        this.name = name;
        this.recurringType = recurringType;
        this.priority = priority;
        this.color = color;
    }

    public String getRecurringTypeDetails() {
        return "";
    }

}
