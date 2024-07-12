package com.motivaa.control;

import com.motivaa.control.errorHandling.MotivaaException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
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

        MotivaaException motivaaException = assertThrows(
                MotivaaException.class,
                () -> userCreationService.createUser(validEmail, validFirstName, validLastName),
                "Expected createUser to throw MotivaaException, but it didn't"
        );

        String errorCode = motivaaException.getErrorCode();
        String errorMessage = motivaaException.getMessage();
        assertEquals("INTERNAL_SERVER_ERROR", errorCode, "Expected error code to be INTERNAL_SERVER_ERROR");
        assertEquals("Error while saving user to the database", errorMessage, "Expected error message to be 'Error while saving user to the database'");
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

