package com.motivaa.control;

import com.motivaa.control.error_handling.exceptions.FieldCustomValidationException;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.control.utility.PossibleRecurringTypes;
import com.motivaa.entity.DaySpecificHabit;
import com.motivaa.entity.Habit;
import com.motivaa.entity.HabitEntry;
import com.motivaa.entity.NotDaySpecificHabit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Log4j2
public class HabitCreationService {

    CommonHabitServices commonHabitServices;
    MotivaaRepository motivaaRepository;
    HabitEntryCreationService habitEntryCreationService;

    public HabitCreationService(
            MotivaaRepository motivaaRepository,
            CommonHabitServices commonHabitServices
            , HabitEntryCreationService habitEntryCreationService
    ) {
        this.motivaaRepository = motivaaRepository;
        this.commonHabitServices = commonHabitServices;
        this.habitEntryCreationService = habitEntryCreationService;
    }

    public Habit createHabit(String userUuid,
                            String name,
                            String recurringType,
                            List<String> listOfRecurringDays,
                            Integer numberOfOccasionsInWeek,
                            Integer priority,
                            String color) {
        commonHabitServices.validateIfUserExists(userUuid);
        commonHabitServices.validateRecurringFieldsForType(recurringType, listOfRecurringDays,numberOfOccasionsInWeek);

        Habit habit;
        HabitEntry habitEntry;
        if (recurringType.equals(PossibleRecurringTypes.SPECIFIC_DAY.toString())) {
            habit = new DaySpecificHabit(
                    userUuid,
                    name,
                    recurringType,
                    listOfRecurringDays,
                    priority,
                    color);

            habitEntry = habitEntryCreationService.createHabitEntryFromDaySpecificHabit(
                    habit.getUuid(),
                    habit.getRecurringType(),
                    listOfRecurringDays);

        } else if ( recurringType.equals(PossibleRecurringTypes.NON_SPECIFIC_DAY.toString()) ) {
            habit = new NotDaySpecificHabit(
                    userUuid,
                    name,
                    recurringType,
                    numberOfOccasionsInWeek,
                    priority,
                    color);

            habitEntry = habitEntryCreationService.createHabitEntryFromNotDaySpecificHabit(
                    habit.getUuid(),
                    habit.getRecurringType(),
                    numberOfOccasionsInWeek);

        } else throw new FieldCustomValidationException(MessageBundle.MISSING_OR_INVALID_RECURRING_TYPE_ERROR_MESSAGE);

        try {
            //TODO reni think through transaction rollback handling
            motivaaRepository.saveHabit(habit);
            motivaaRepository.saveHabitEntry(habitEntry);
        } catch (IOException e) {
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        return habit;
    }
}
