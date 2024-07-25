package com.motivaa.control.utility;

public class MessageBundle {

    private MessageBundle() {
        throw new UnsupportedOperationException("Constants is a utility class and cannot be instantiated");
    }

    public static final String INTERNAL_ERROR_RESPONSE = "Some error happened. Please try again later.";
    public static final String INVALID_COLOR_RESPONSE = "Invalid color. Colors are only accepted from the pre-defined list";
    public static final String INVALID_RECURRING_DAYS_RESPONSE = "Invalid recurring days. Recurring days are only accepted from the pre-defined list";

    public static final String MISSING_UUID_ERROR_MESSAGE = "User UUID is mandatory";
    public static final String MISSING_NAME_ERROR_MESSAGE = "Name is mandatory";
    public static final String MISSING_RECURRING_TYPE_ERROR_MESSAGE = "Recurring type is mandatory";
    public static final String INVALID_UUID_FORMAT_ERROR_MESSAGE = "Invalid User UUID format";
    public static final String INVALID_NAME_FORMAT_ERROR_MESSAGE = "Invalid name format";
    public static final String INVALID_NUMBER_OF_OCCASIONS_IN_WEEK_ERROR_MESSAGE = "Number of occasions in a week must be between 1 and 7";
    public static final String INVALID_PRIORITY_ERROR_MESSAGE = "Priority must be between 1 and 5";
    public static final String INVALID_RECURRING_TYPE_ERROR_MESSAGE = "Invalid recurring type. Recurring type must be a value from the pre-defined list";
    public static final String MISSING_PRIORITY_ERROR_MESSAGE = "Priority is mandatory";
    public static final String MISSING_COLOR_ERROR_MESSAGE = "Color is mandatory";


    public static final String RECURRING_DAYS_MANDATORY_SPECIFIC_DAY = "listOfRecurringDays is mandatory for specific_day type";
    public static final String NUMBER_OF_OCCASIONS_NOT_ALLOWED_SPECIFIC_DAY = "numberOfOccasionsInWeek is not allowed for specific_day type";
    public static final String RECURRING_DAYS_IS_NOT_ALLOWED_SPECIFIC_DAY = "listOfRecurringDays is not allowed for non_specific_day type";
    public static final String NUMBER_OF_OCCASIONS_MANDATORY_NON_SPECIFIC_DAY = "numberOfOccasionsInWeek is mandatory for non_specific_day type";
    public static final String USER_NOT_FOUND = "User not found";
}
