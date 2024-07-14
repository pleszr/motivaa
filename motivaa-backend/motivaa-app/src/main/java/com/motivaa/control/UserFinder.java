package com.motivaa.control;

import com.motivaa.control.errorHandling.ErrorCode;
import com.motivaa.control.errorHandling.MotivaaException;
import com.motivaa.control.errorHandling.exceptions.NotFoundException;
import com.motivaa.control.repository.MotivaaRepository;
import com.motivaa.entity.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Log4j2
@Service
public class UserFinder {
    private final MotivaaRepository motivaaRepository;

    UserFinder(MotivaaRepository motivaaRepository) {
        this.motivaaRepository = motivaaRepository;
    }

    public List<User> searchAllUsers() {
        List<User> userList;
        try {
            userList = motivaaRepository.searchAllUsers();
        } catch (IOException e) {
            log.error("IOException at searchAllUsers",e);
            throw new MotivaaException(ErrorCode.INTERNAL_SERVER_ERROR,"Error while retrieving all users");
        }
        return userList;
    }

    public User findUserByUuid(String uuid) {
        User user;
        try {
            user = motivaaRepository.findUserByUuid(uuid);
        } catch (IOException e) {
            log.error("IOException at findUserByUuid",e);
            throw new MotivaaException(ErrorCode.INTERNAL_SERVER_ERROR, String.format("Error while searching user by uuid: %s", uuid));
        }
        if (user == null) {
            throw new NotFoundException(String.format("User with uuid: %s, not found", uuid));
        }
        return user;
    }
}