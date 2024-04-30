package com.habito.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;
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
