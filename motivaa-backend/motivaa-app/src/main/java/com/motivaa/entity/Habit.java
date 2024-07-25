package com.motivaa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@NoArgsConstructor
@Data
public abstract  class Habit {
    private UUID uuid;
    private String userUuid;
    private String name;
    private String recurringType;
    private Integer priority;
    private String color;

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
