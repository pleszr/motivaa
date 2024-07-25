package com.motivaa.TestUtils;

import java.util.HashMap;

public class HabitRequestCreator {
    public static String VALID_USER_UUID = "123e4567-e89b-12d3-a456-426614174000";
    public static String VALID_NAME = "HabitName";
    public static String VALID_RECURRING_TYPE_SPECIFIC_DAY = "SPECIFIC_DAY";
    public static String VALID_RECURRING_TYPE_NON_SPECIFIC_DAY = "NON_SPECIFIC_DAY";
    public static String VALID_LIST_OF_RECURRING_DAYS = "MONDAY;TUESDAY;WEDNESDAY";
    public static String VALID_NUMBER_OF_OCCASIONS_IN_WEEK = "3";
    public static String VALID_PRIORITY = "3";
    public static String VALID_COLOR = "RED";

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

}
