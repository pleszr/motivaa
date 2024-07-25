package com.motivaa.control;

import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.Habit;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class HabitFinderService {
    MotivaaRepository motivaaRepository;

    public HabitFinderService(MotivaaRepository motivaaRepository) {
        this.motivaaRepository = motivaaRepository;
    }
    public List<Habit> habitFinder(String userUuid) {
        List<Habit> habits;
        try {
            habits = motivaaRepository.searchHabitByUserUuid(UUID.fromString(userUuid));
        } catch (IOException e) {
            log.error("Repository exception. Stack trace:",e);
            throw new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE);
        }
        return habits;
    }
}
