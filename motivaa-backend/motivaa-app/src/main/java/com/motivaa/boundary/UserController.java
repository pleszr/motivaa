package com.motivaa.boundary;

import com.motivaa.boundary.dto.*;
import com.motivaa.boundary.validators.UuidValidator;
import com.motivaa.control.UserCreationService;
import com.motivaa.control.UserFinder;
import com.motivaa.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user-apis")
@Tag(name = "User API", description = "Endpoint to expose user management operations")
@Log4j2
public class UserController {
    UserCreationService userCreationService;
    UserFinder userFinder;

    UserController(UserCreationService userCreationService,
                   UserFinder userFinder){
        this.userCreationService = userCreationService;
        this.userFinder = userFinder;
    }

    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User created successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreateUserResponse.class))
                    })
    })
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

    @Operation(summary = "Search all users")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returned users successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SearchAllUsersResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "204",
                    description = "Returned 0 users successfully",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping("/users")
    public ResponseEntity<List<SearchAllUsersResponse>> searchAllUsers() {
        List<User> users = userFinder.searchAllUsers();
        List<SearchAllUsersResponse> responseList = users.stream().map(SearchAllUsersResponse::fromUser).toList();
        if (responseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Find user by UUID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Returned user successfully",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FindUserByUuidResponse.class))
                    }),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content)
    })
    @GetMapping("/users/{userUuid}")
    public ResponseEntity<FindUserByUuidResponse> findUserByUuid(@PathVariable("userUuid") String userUuid) {
        UuidValidator.validateUuid(userUuid);
        User user = userFinder.findUserByUuid(userUuid);
        return ResponseEntity.ok(FindUserByUuidResponse.fromUser(user));
    }
}
