package com.motivaa.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Habit {
    UUID uuid;
    String userUuid;
    String name;
    String recurringType;
    String recurringTypeDetails;
    Integer priority;
    String color;

    public Habit(String userUuid,
                 String name,
                 String recurringType,
                 String recurringTypeDetails,
                 Integer priority,
                 String color) {
        this.uuid = UUID.randomUUID();
        this.userUuid = userUuid;
        this.name = name;
        this.recurringType = recurringType;
        this.recurringTypeDetails = recurringTypeDetails;
        this.priority = priority;
        this.color = color;
    }

}
