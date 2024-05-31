package com.motivaa.control;

import com.motivaa.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MotivaaRepository extends CrudRepository<User, String>{
    Optional<User>findByUuid(String uuid);
}
