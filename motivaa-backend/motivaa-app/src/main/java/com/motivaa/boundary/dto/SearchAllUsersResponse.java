package com.motivaa.boundary.dto;

import com.motivaa.entity.User;
import lombok.Value;

import java.sql.Timestamp;
import java.util.UUID;

@Value
public class SearchAllUsersResponse {
    UUID uuid;
    String email;
    String firstName;
    String lastName;
    String status;
    Timestamp createdTimestamp;
    Timestamp lastLoginTimestamp;

    private SearchAllUsersResponse(User user) {
        this.uuid = user.getUuid();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.status = user.getStatus();
        this.createdTimestamp = user.getCreatedTimestamp();
        this.lastLoginTimestamp = user.getLastLoginTimestamp();
    }

    public static SearchAllUsersResponse fromUser (User user) {
        return new SearchAllUsersResponse(user);
    }
}
