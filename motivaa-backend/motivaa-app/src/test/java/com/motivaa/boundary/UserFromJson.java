package com.motivaa.boundary;

import lombok.Data;

@Data
public class UserFromJson {
    String uuid;
    String email;
    String firstName;
    String lastName;
    String status;
    String createdTimestamp;
    String lastLoginTimestamp;
}
