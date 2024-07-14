package com.motivaa.control;

import com.motivaa.control.errorHandling.exceptions.NotFoundException;
import com.motivaa.control.errorHandling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.entity.User;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class UserFinderTest {
    String validEmail = "unit@test.com";
    String validFirstName = "Unit";
    String validLastName = "Test";

    String validEmail2 = "unit2@test.com";
    String validFirstName2 = "Unit2";
    String validLastName2 = "Test2";

    @MockBean
    private MotivaaRepository motivaaRepository;

    @Autowired
    UserFinder userFinder;

    @Nested
    class searchAllUsers {

        @Test
        void searchAllUsers_Success() throws IOException {
            User user1 = new User(validEmail, validFirstName, validLastName);
            User user2 = new User(validEmail2, validFirstName2, validLastName2);
            List<User> userList = List.of(user1, user2);

            when(motivaaRepository.searchAllUsers()).thenReturn(userList);

            assertDoesNotThrow(() -> {
                userFinder.searchAllUsers();
            });
            Integer listSize = userFinder.searchAllUsers().size();
            assertEquals(2,
                    listSize,
                    "Expected 2 users to be returned, but " + listSize + " users were returned");
        }

        @Test
        void searchAllUsers_HandleIOException() throws IOException {
            when(motivaaRepository.searchAllUsers()).thenThrow(new IOException());

            RepositoryException repositoryException = assertThrows(
                    RepositoryException.class,
                    () -> userFinder.searchAllUsers(),
                    "Expected createUser to throw RepositoryException, but it didn't"
            );

            String errorMessage = repositoryException.getMessage();
            assertEquals("Some error happened. Please try again later.", errorMessage);
        }

        @Test
        void searchAllUsers_BreakAtUnhandledException() throws IOException {
            when(motivaaRepository.searchAllUsers()).thenThrow(new RuntimeException());

            assertThrows(
                    RuntimeException.class,
                    () -> userFinder.searchAllUsers(),
                    "Expected createUser to throw RuntimeException, but it didn't"
            );
        }
    }
    @Nested
    class FindUserByUuid {
        @Test
        void findUserByUuid_Success() throws IOException {
            User userOriginal = new User(validEmail, validFirstName, validLastName);
            when(motivaaRepository.findUserByUuid("uuid")).thenReturn(userOriginal);

            User userReturned = assertDoesNotThrow(() -> userFinder.findUserByUuid("uuid"));

            assertEquals(
                    userOriginal.getUuid(),
                    userReturned.getUuid(),
                    "Expected the same user to be returned, but it wasn't");
        }

        @Test
        void findUserByUuid_HandleIOException() throws IOException {
            when(motivaaRepository.findUserByUuid("uuid")).thenThrow(new IOException());

            RepositoryException repositoryException = assertThrows(
                    RepositoryException.class,
                    () -> userFinder.findUserByUuid("uuid"),
                    "Expected createUser to throw RepositoryException, but it didn't"
            );

            String errorMessage = repositoryException.getMessage();
            assertEquals("Some error happened. Please try again later.", errorMessage);
        }

        @Test
        void findUserByUuid_BreakAtUnhandledException() throws IOException {
            when(motivaaRepository.findUserByUuid("uuid")).thenThrow(new RuntimeException());

            assertThrows(
                    RuntimeException.class,
                    () -> userFinder.findUserByUuid("uuid"),
                    "Expected createUser to throw RuntimeException, but it didn't"
            );
        }

        @Test
        void findUserByUuid_UserNotFound() throws IOException {
            when(motivaaRepository.findUserByUuid("uuid")).thenReturn(null);

            NotFoundException notFoundException = assertThrows(
                    NotFoundException.class,
                    () -> userFinder.findUserByUuid("uuid"),
                    "Expected createUser to throw NotFoundException, but it didn't"
            );

            String errorMessage = notFoundException.getMessage();
            assertEquals("User with uuid: uuid, not found", errorMessage);
        }
    }


}
