package com.motivaa.control;

import com.motivaa.control.errorHandling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.Habit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
                            String recurringTypeDetails,
                            Integer priority,
                            String color) {
        HabitUtils.validateIfUserExists(userUuid, motivaaRepository);
        HabitUtils.validateRecurringFieldsForType(recurringType, recurringTypeDetails);
        Habit habit = new Habit(
                userUuid,
                name,
                recurringType,
                recurringTypeDetails,
                priority,
                color);
        try {
            motivaaRepository.saveHabit(habit);
        } catch (IOException e) {
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        return habit;
    }
}
