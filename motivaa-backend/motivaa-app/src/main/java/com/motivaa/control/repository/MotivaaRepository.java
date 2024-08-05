package com.motivaa.control.repository;

import com.motivaa.entity.Habit;
import com.motivaa.entity.HabitEntry;
import com.motivaa.entity.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class MotivaaRepository{
    Elasticsearch elasticsearch;

    MotivaaRepository(Elasticsearch elasticsearch){
        this.elasticsearch = elasticsearch;
    }

    public List<User> searchAllUsers() throws java.io.IOException{
        return elasticsearch.searchAllUsers();
    }

    public void saveUser(User user) throws java.io.IOException {
        elasticsearch.saveUser(user);
    }

    public User findUserByUuid(String uuid) throws java.io.IOException {
        return elasticsearch.findUserById(java.util.UUID.fromString(uuid));
    }

    public void saveHabit(Habit habit) throws java.io.IOException {
        elasticsearch.saveHabit(habit);
    }

    public List<Habit> searchHabitByUserUuid(UUID userUuid) throws IOException {
        return elasticsearch.searchHabitByUserUuid(userUuid);
    }

    public void saveHabitEntry(HabitEntry habitEntry) throws IOException {
        elasticsearch.saveHabitEntry(habitEntry);
    }


}
