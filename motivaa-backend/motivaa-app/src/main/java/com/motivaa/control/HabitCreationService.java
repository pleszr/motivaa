package com.motivaa.control;

import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.entity.Habit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
                            String recurringDetails,
                            Integer priority,
                            String color) {
        Habit habit = new Habit(userUuid, name, recurringType, recurringDetails, priority, color);
        try {
            motivaaRepository.saveHabit(habit);
        } catch (Exception e) {
            log.error("Error while saving habit to the database: {}", e.getMessage());
        }
        return habit;
    }


}
