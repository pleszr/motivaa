package com.motivaa.control;

import com.motivaa.TestUtils.HabitFactory;
import com.motivaa.entity.HabitEntry;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
public class HabitEntryCreationServiceTest {
    @Autowired
    CommonHabitServices commonHabitServices;
    @Autowired
    HabitEntryCreationService habitEntryCreationService;

    @Nested
    class CreateHabitEntryFromDaySpecificHabit {
        void giving_valid_date_it_should_successfully_generate_habitentry_from_day_specific_habit() {

            HabitEntry habitEntry= habitEntryCreationService.createHabitEntryFromDaySpecificHabit(
                    HabitFactory.VALID_HABIT_UUID,
                    HabitFactory.VALID_RECURRING_TYPE_SPECIFIC_DAY,
                    HabitFactory.VALID_LIST_OF_RECURRING_DAYS
            );

            



        }



    }
}
