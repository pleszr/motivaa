package com.habito.control;

import com.habito.entity.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class HabitoService {
    private final HabitoRepository habitoRepository;

    HabitoService(HabitoRepository habitoRepository) {
        this.habitoRepository = habitoRepository;
    }

    public User createRandomUser() {
        User user = new User();
        user.setFirstName("John");
        habitoRepository.save(user);
        return user;
    }
}
