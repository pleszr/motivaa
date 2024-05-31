package com.motivaa.control;

import com.motivaa.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MotivaaService {
    private final MotivaaRepository motivaaRepository;

    MotivaaService(MotivaaRepository motivaaRepository) {
        this.motivaaRepository = motivaaRepository;
    }

    public User createRandomUser() {
        User user = new User();
        user.setFirstName("John");
        motivaaRepository.save(user);
        return user;
    }
}
