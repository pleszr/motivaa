package com.motivaa.control;

import com.motivaa.control.errorHandling.exceptions.RepositoryException;
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
    MotivaaRepository motivaaRepository;

    HabitCreationService(MotivaaRepository motivaaRepository){
        this.motivaaRepository = motivaaRepository;
    }
    public Habit createHabit(String userUuid,
                            String name,
                            String recurringType,
                            String listOfRecurringDays,
                            Integer numberOfOccasionsInWeek,
                            Integer priority,
                            String color) {
        HabitUtils.validateIfUserExists(userUuid, motivaaRepository);
        HabitUtils.validateRecurringFieldsForType(recurringType, listOfRecurringDays,numberOfOccasionsInWeek);
        Habit habit = switch (recurringType) {
            case "specific_day" -> new DaySpecificHabit(
                    userUuid,
                    name,
                    recurringType,
                    Arrays.asList(listOfRecurringDays.split(";")),
                    priority,
                    color);
            case "non_specific_day" -> new NotDaySpecificHabit(
                    userUuid,
                    name,
                    recurringType,
                    numberOfOccasionsInWeek,
                    priority,
                    color);
            default -> throw new IllegalArgumentException("Invalid recurringType");
        };
        try {
            motivaaRepository.saveHabit(habit);
        } catch (IOException e) {
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        return habit;
    }

//    private Habit createSpecificHabit

}
