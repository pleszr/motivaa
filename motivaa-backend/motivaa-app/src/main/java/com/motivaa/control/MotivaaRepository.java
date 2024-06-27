package com.motivaa.control;

import com.motivaa.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotivaaRepository{
    ESClient esClient;

    MotivaaRepository(ESClient esClient){
        this.esClient = esClient;
    }

    public void save(User user){
        esClient.saveUser(user);
    }

    public List<User> searchUserByFirstName(String firstName) throws java.io.IOException{
        return esClient.searchUserByFirstName(firstName);
    }


}
