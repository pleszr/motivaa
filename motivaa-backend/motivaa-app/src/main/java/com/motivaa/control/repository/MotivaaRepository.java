package com.motivaa.control.repository;

import com.motivaa.entity.Habit;
import com.motivaa.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MotivaaRepository{
    ESClient esClient;

    MotivaaRepository(ESClient esClient){
        this.esClient = esClient;
    }

    public List<User> searchAllUsers() throws java.io.IOException{
        return esClient.searchAllUsers();
    }

    public void saveUser(User user) throws java.io.IOException {
        esClient.saveUser(user);
    }

    public User findUserByUuid(String uuid) throws java.io.IOException {
        return esClient.findUserById(java.util.UUID.fromString(uuid));
    }

    public void saveHabit(Habit habit) throws java.io.IOException {
        esClient.saveHabit(habit);
    }

    public List<Habit> searchHabitByUserUuid(UUID userUuid) throws IOException {
        return esClient.searchHabitByUserUuid(userUuid);
    }


}
