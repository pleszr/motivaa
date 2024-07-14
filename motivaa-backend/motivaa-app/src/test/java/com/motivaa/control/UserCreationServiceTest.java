package com.motivaa.control;

import com.motivaa.control.errorHandling.exceptions.RepositoryException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ActiveProfiles("test")
public class UserCreationServiceTest {
    String validEmail = "unit@test.com";
    String validFirstName = "Unit";
    String validLastName = "Test";

    @MockBean
    private MotivaaRepository motivaaRepository;

    @Autowired
    UserCreationService userCreationService;


    @Test
    void testCreateUser_Success() {
        assertDoesNotThrow(() -> {
            User user = userCreationService.createUser(validEmail, validFirstName, validLastName);
            assertEquals(validEmail, user.getEmail(), "Expected email to be" + validEmail + " but was " + user.getEmail());
            assertEquals(validFirstName, user.getFirstName(), "Expected first name to be" + validFirstName + " but was " + user.getFirstName());
            assertEquals(validLastName, user.getLastName(), "Expected last name to be" + validLastName + " but was " + user.getLastName());
        });
     }

    @Test
    void testCreateUser_HandleIOException() throws IOException{
        doThrow(new IOException()).
                when(motivaaRepository).
                saveUser(any(User.class));

        RepositoryException repositoryException = assertThrows(
                RepositoryException.class,
                () -> userCreationService.createUser(validEmail, validFirstName, validLastName),
                "Expected createUser to throw RepositoryException, but it didn't"
        );

        String errorMessage = repositoryException.getMessage();
        assertEquals("Some error happened. Please try again later.", errorMessage);
    }

    @Test
    void testCreateUser_BreakAtUnhandledException() throws IOException{

        doThrow(new RuntimeException()).
                when(motivaaRepository).
                saveUser(any(User.class));

        assertThrows(
                RuntimeException.class,
                () -> userCreationService.createUser(validEmail, validFirstName, validLastName),
                "Expected createUser to throw RuntimeException, but it didn't"
        );
    }
}

