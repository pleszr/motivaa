package com.motivaa.TestUtils;

import com.motivaa.entity.DaySpecificHabit;
import com.motivaa.entity.Habit;
import com.motivaa.entity.NotDaySpecificHabit;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;

public class HabitFactory {
    public static String VALID_USER_UUID = "123e4567-e89b-12d3-a456-426614174000";
    public static String VALID_NAME = "HabitName";
    public static String VALID_RECURRING_TYPE_SPECIFIC_DAY = "SPECIFIC_DAY";
    public static String VALID_RECURRING_TYPE_NON_SPECIFIC_DAY = "NON_SPECIFIC_DAY";
    public static String VALID_LIST_OF_RECURRING_DAYS = "MONDAY;TUESDAY;WEDNESDAY";
    public static String VALID_NUMBER_OF_OCCASIONS_IN_WEEK = "3";
    public static String VALID_PRIORITY = "3";
    public static String VALID_COLOR = "RED";

    public static DaySpecificHabit createDaySpecificHabit(){
        return new DaySpecificHabit(
                VALID_USER_UUID,
                VALID_NAME,
                VALID_RECURRING_TYPE_SPECIFIC_DAY,
                Arrays.stream(VALID_LIST_OF_RECURRING_DAYS.split(";")).toList(),
                Integer.parseInt(VALID_PRIORITY),
                VALID_COLOR);
    }

    public static NotDaySpecificHabit createNotDaySpecificHabit(){
        return new NotDaySpecificHabit(
                VALID_USER_UUID,
                VALID_NAME,
                VALID_RECURRING_TYPE_NON_SPECIFIC_DAY,
                Integer.parseInt(VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                Integer.parseInt(VALID_PRIORITY),
                VALID_COLOR);
    }

    public static HashMap<String,String> createRequest_SUCCESS_SPECIFIC_DAY(){
        HashMap<String, String> request = new HashMap<>();
        request.put("userUuid", VALID_USER_UUID);
        request.put("name", VALID_NAME);
        request.put("recurringType", VALID_RECURRING_TYPE_SPECIFIC_DAY);
        request.put("listOfRecurringDays", VALID_LIST_OF_RECURRING_DAYS);
        request.put("priority", VALID_PRIORITY);
        request.put("color", VALID_COLOR);
        return request;
    }

    public static HashMap<String,String> createRequest_SUCCESS_NON_SPECIFIC_DAY(){
        HashMap<String, String> request = new HashMap<>();
        request.put("userUuid", VALID_USER_UUID);
        request.put("name", VALID_NAME);
        request.put("recurringType", VALID_RECURRING_TYPE_NON_SPECIFIC_DAY);
        request.put("numberOfOccasionsInWeek", VALID_NUMBER_OF_OCCASIONS_IN_WEEK);
        request.put("priority", VALID_PRIORITY);
        request.put("color", VALID_COLOR);
        return request;
    }

    public static void assertHabitDidntChange(Habit habit) {
        assertEquals(habit.getUserUuid(), VALID_USER_UUID);
        assertEquals(habit.getName(), VALID_NAME);
        assertEquals(habit.getPriority(), Integer.parseInt(VALID_PRIORITY));

        if (habit instanceof DaySpecificHabit) {
            assertEquals(habit.getRecurringType(), VALID_RECURRING_TYPE_SPECIFIC_DAY);
            assertEquals(
                    Arrays.stream(VALID_LIST_OF_RECURRING_DAYS.split(";")).toList(),
                    ((DaySpecificHabit) habit).getListOfRecurringDays()
            );}
        else if (habit instanceof NotDaySpecificHabit) {
            assertEquals(habit.getRecurringType(), VALID_RECURRING_TYPE_NON_SPECIFIC_DAY);

            assertEquals(
                    Integer.parseInt(VALID_NUMBER_OF_OCCASIONS_IN_WEEK),
                    ((NotDaySpecificHabit) habit).getNumberOfOccasionsInWeek()
            );}
    }

}
