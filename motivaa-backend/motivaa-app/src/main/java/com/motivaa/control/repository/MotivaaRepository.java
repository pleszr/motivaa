package com.motivaa.control.repository;

import com.motivaa.entity.Habit;
import com.motivaa.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

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


}
