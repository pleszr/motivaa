package com.motivaa.control;

import com.motivaa.control.errorHandling.ErrorCode;
import com.motivaa.control.errorHandling.MotivaaException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Log4j2
@Service
public class UserCreationService {
    MotivaaRepository motivaaRepository;

    UserCreationService(MotivaaRepository motivaaRepository){
        this.motivaaRepository = motivaaRepository;
    }

    public User createUser(String email, String firstName, String lastName) {
        User user = new User(email, firstName, lastName);
        try {
            motivaaRepository.saveUser(user);
        } catch (IOException e) {
            throw new MotivaaException(ErrorCode.INTERNAL_SERVER_ERROR,"Error while saving user to the database");
        }
        return user;
    }
}
