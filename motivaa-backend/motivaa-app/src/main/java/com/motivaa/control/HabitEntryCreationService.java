package com.motivaa.control;

import com.motivaa.control.utility.PossibleDayStatuses;
import com.motivaa.entity.DailyData;
import com.motivaa.entity.HabitEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HabitEntryCreationService {
    CommonHabitServices commonHabitServices;

    HabitEntryCreationService(CommonHabitServices commonHabitServices) {
        this.commonHabitServices = commonHabitServices;
    }

    public HabitEntry createHabitEntryFromDaySpecificHabit(
            UUID habitUuid,
            String recurringType,
            List<String> listOfRecurringDays) {

        List<DailyData> weeklyData = createWeeklyDataWithDailyData(listOfRecurringDays);

        return new HabitEntry(
                UUID.randomUUID(),
                habitUuid,
                commonHabitServices.generateWeekId(),
                recurringType,
                weeklyData);
    }

    public HabitEntry createHabitEntryFromNotDaySpecificHabit(
            UUID habitUuid,
            String recurringType,
            Integer numberOfOccasionsInWeek) {

        List<String> numberOfOccasionsInWeekList = createListOfStringsFromInteger(numberOfOccasionsInWeek);

        List<DailyData> weeklyData = createWeeklyDataWithDailyData(numberOfOccasionsInWeekList);

        return new HabitEntry(
                UUID.randomUUID(),
                habitUuid,
                commonHabitServices.generateWeekId(),
                recurringType,
                weeklyData);
    }

    private List<String> createListOfStringsFromInteger(Integer integer) {
        List<String> listOfString = new ArrayList<>();
        for (Integer i = 1; i<= integer; i++) {
            listOfString.add(String.valueOf(i));
        }
        return listOfString;
    }

    private List<DailyData> createWeeklyDataWithDailyData(List<String> listOfRecurringDays) {
        List<DailyData> weeklyData = new ArrayList<>();
        for (String label: listOfRecurringDays) {
            DailyData dailyData = new DailyData(
                    label,
                    PossibleDayStatuses.EMPTY.toString());
            weeklyData.add(dailyData);
        }
        return weeklyData;
    }
}
