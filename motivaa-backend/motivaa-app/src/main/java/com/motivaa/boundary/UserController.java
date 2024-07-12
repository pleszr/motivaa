package com.motivaa.boundary;

import com.motivaa.boundary.dto.CreateUserRequest;
import com.motivaa.boundary.dto.CreateUserResponse;
import com.motivaa.boundary.dto.FindUserByUuidRequestValidator;
import com.motivaa.boundary.dto.FindUserByUuidResponse;
import com.motivaa.control.UserCreationService;
import com.motivaa.control.UserFinder;
import com.motivaa.entity.User;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user-apis")
@Log4j2
public class UserController {
    UserCreationService userCreationService;
    UserFinder userFinder;

    UserController(UserCreationService userCreationService,
                   UserFinder userFinder){
        this.userCreationService = userCreationService;
        this.userFinder = userFinder;
    }

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        log.trace("User creation for user started with details: {}", createUserRequest);
        User user = userCreationService.createUser(
                        createUserRequest.getEmail(),
                        createUserRequest.getFirstName(),
                        createUserRequest.getLastName());
        log.info("User creation for user finished with details: {}", user);
        return ResponseEntity.ok(CreateUserResponse.fromUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<CreateUserResponse>> searchAllUsers() {
        List<User> users = userFinder.searchAllUsers();
        List<CreateUserResponse> dtoList = users.stream().map(CreateUserResponse::fromUser).toList();
        return ResponseEntity.ok(dtoList);
    }

    //success_two_users
    //success_0_users
    //motivaa exception

    @GetMapping("/users/{userUuid}")
    public ResponseEntity<FindUserByUuidResponse> findUserByUuid(@PathVariable("userUuid") String userUuid) {
        FindUserByUuidRequestValidator.validateRequest(userUuid);
        User user = userFinder.findUserByUuid(userUuid);
        return ResponseEntity.ok(FindUserByUuidResponse.fromUser(user));
    }
}
