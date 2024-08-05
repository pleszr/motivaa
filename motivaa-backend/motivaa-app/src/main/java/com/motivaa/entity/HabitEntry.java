package com.motivaa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitEntry {
    private UUID uuid;
    private UUID habitUuid;
    private String weekId;
    private String recurringType;
    private List<DailyData> weeklyData;
}
