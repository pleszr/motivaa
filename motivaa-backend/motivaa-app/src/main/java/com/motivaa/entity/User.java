package com.motivaa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String uuid;
    String username;
    String email;
    String firstName;
    String lastName;
    String status;
    Timestamp lastLoginTimeStamp;
}
