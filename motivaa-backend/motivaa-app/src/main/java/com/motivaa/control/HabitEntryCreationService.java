package com.motivaa.control;

import com.motivaa.control.utility.PossibleDayStatuses;
import com.motivaa.entity.HabitEntry;
import com.motivaa.entity.WeeklyData;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class HabitEntryCreationService {
    CommonHabitServices commonHabitServices;

    HabitEntryCreationService(CommonHabitServices commonHabitServices) {
        this.commonHabitServices = commonHabitServices;
    }

    public HabitEntry createHabitEntryFromDaySpecificHabit(UUID habitUuid,
                                       String recurringType,
                                       String listOfRecurringDays) {
        String weekId = commonHabitServices.generateWeekId();

        WeeklyData weeklyData = new WeeklyData(
                weekId,
                PossibleDayStatuses.EMPTY.toString());


        HabitEntry habitEntry = new HabitEntry(
                    UUID.randomUUID(),
                    habitUuid,
                    weekId,
                    recurringType,
                    Collections.EMPTY_LIST);
            return habitEntry;

}




}
