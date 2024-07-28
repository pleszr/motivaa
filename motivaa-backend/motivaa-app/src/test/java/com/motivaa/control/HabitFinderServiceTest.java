package com.motivaa.control;


import com.motivaa.TestUtils.HabitFactory;
import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.control.utility.MessageBundle;
import com.motivaa.entity.Habit;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
public class HabitFinderServiceTest {
    @MockBean
    private MotivaaRepository motivaaRepository;

    @Autowired
    private HabitFinderService habitFinderService;

    @Nested
    class Success_scenarios {


        @Test
        void search_with_two_results_should_give_back_2_results() throws Exception {
            Habit habit1 = HabitFactory.createDaySpecificHabit();
            Habit habit2 = HabitFactory.createNotDaySpecificHabit();
            List<Habit> originalHabitList = List.of(habit1, habit2);

            when(motivaaRepository.searchHabitByUserUuid(
                    UUID.fromString(HabitFactory.VALID_USER_UUID))
            ).thenReturn(originalHabitList);

            List<Habit> returnedHabitList = habitFinderService.habitFinder(HabitFactory.VALID_USER_UUID);

            assertEquals(
                    originalHabitList,
                    returnedHabitList
            );

            returnedHabitList.forEach(
                    HabitFactory::assertHabitDidntChange
            );
        }

        @Test
        void search_with_no_results_should_give_back_an_empty_list() throws Exception {
            when(motivaaRepository.searchHabitByUserUuid(
                    UUID.fromString(HabitFactory.VALID_USER_UUID))
            ).thenReturn(Collections.emptyList());

            List<Habit> returnedHabitList = habitFinderService.habitFinder(HabitFactory.VALID_USER_UUID);

            assertArrayEquals(
                    new ArrayList<>().toArray(),
                    returnedHabitList.toArray()
            );
        }
    }
    @Nested
    class Failure_scenarios {
        @Test
        void given_search_throws_IOException_should_throw_RepositoryException() throws Exception {
            when(motivaaRepository.searchHabitByUserUuid(
                    UUID.fromString(HabitFactory.VALID_USER_UUID))
            ).thenThrow(new RepositoryException(MessageBundle.INTERNAL_ERROR_RESPONSE));

            Exception e = assertThrows(
                    RepositoryException.class,
                    () -> habitFinderService.habitFinder(HabitFactory.VALID_USER_UUID)
            );
            assertEquals(MessageBundle.INTERNAL_ERROR_RESPONSE, e.getMessage());
        }
    }


}
