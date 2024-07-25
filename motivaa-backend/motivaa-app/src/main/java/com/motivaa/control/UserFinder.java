package com.motivaa.control;

import com.motivaa.control.error_handling.exceptions.RepositoryException;
import com.motivaa.control.error_handling.exceptions.NotFoundException;
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
            throw new RepositoryException("Some error happened. Please try again later.");
        }
        return userList;
    }

    public User findUserByUuid(String uuid) {
        User user;
        try {
            user = motivaaRepository.findUserByUuid(uuid);
        } catch (IOException e) {
            log.error("IOException at findUserByUuid",e);
            throw new RepositoryException("Some error happened. Please try again later.");
        }
        if (user == null) {
            throw new NotFoundException(String.format("User with uuid: %s, not found", uuid));
        }
        return user;
    }
}