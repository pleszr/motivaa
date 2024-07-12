package com.motivaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@NoArgsConstructor
public class User {
    public User(
            String email,
            String firstName,
            String lastName
    ) {
        this.uuid = UUID.randomUUID();
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = "active";
        this.createdTimestamp = new Timestamp(System.currentTimeMillis());
    }
    @Id
    UUID uuid;
    String email;
    String firstName;
    String lastName;
    String status;
    Timestamp createdTimestamp;
    Timestamp lastLoginTimestamp;
}
