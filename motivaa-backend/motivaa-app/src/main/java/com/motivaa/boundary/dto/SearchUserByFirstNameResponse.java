package com.motivaa.boundary.dto;

import com.motivaa.entity.User;
import lombok.Data;
import java.sql.Timestamp;
import java.util.UUID;

@Data
public class SearchUserByFirstNameResponse {
    UUID uuid;
    String email;
    String firstName;
    String lastName;
    String status;
    Timestamp createdTimestamp;
    Timestamp lastLoginTimestamp;

    private SearchUserByFirstNameResponse(User user) {
        this.uuid = user.getUuid();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.status = user.getStatus();
        this.createdTimestamp = user.getCreatedTimestamp();
        this.lastLoginTimestamp = user.getLastLoginTimestamp();
    }

    public static SearchUserByFirstNameResponse fromUser (User user) {
        return new SearchUserByFirstNameResponse(user);
    }
}
