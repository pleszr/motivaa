package com.motivaa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "habitType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DaySpecificHabit.class, name = "SPECIFIC_DAY"),
        @JsonSubTypes.Type(value = NotDaySpecificHabit.class, name = "NON_SPECIFIC_DAY"),

})
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
    @JsonIgnore
    public String getRecurringTypeDetails() {
        return "";
    }

}
