package com.motivaa.control;

import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.DaySpecificHabit;
import com.motivaa.entity.Habit;
import com.motivaa.entity.NotDaySpecificHabit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

@Service
@Log4j2
public class HabitCreationService {

    CommonHabitServices commonHabitServices;
    MotivaaRepository motivaaRepository;

    HabitCreationService(
            MotivaaRepository motivaaRepository,
            CommonHabitServices commonHabitServices){
        this.motivaaRepository = motivaaRepository;
        this.commonHabitServices = commonHabitServices;
    }

    public Habit createHabit(String userUuid,
                            String name,
                            String recurringType,
                            String listOfRecurringDays,
                            Integer numberOfOccasionsInWeek,
                            Integer priority,
                            String color) {
        commonHabitServices.validateIfUserExists(userUuid);
        commonHabitServices.validateRecurringFieldsForType(recurringType, listOfRecurringDays,numberOfOccasionsInWeek);
        Habit habit = switch (recurringType) {
            case "SPECIFIC_DAY" -> new DaySpecificHabit(
                    userUuid,
                    name,
                    recurringType,
                    Arrays.asList(listOfRecurringDays.split(";")),
                    priority,
                    color);
            case "NON_SPECIFIC_DAY" -> new NotDaySpecificHabit(
                    userUuid,
                    name,
                    recurringType,
                    numberOfOccasionsInWeek,
                    priority,
                    color);
            default -> throw new FieldCustomValidationException(MessageBundle.INVALID_RECURRING_TYPE_ERROR_MESSAGE);
        };
        try {
            motivaaRepository.saveHabit(habit);
        } catch (IOException e) {
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        return habit;
    }
}
