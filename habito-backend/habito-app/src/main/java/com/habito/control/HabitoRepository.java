package com.habito.control;

import com.habito.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface HabitoRepository extends CrudRepository<User, String>{
    Optional<User>findByUuid(String uuid);
}
