package com.bss.restaurant.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Getter
@Builder
public class UserResponse {
    private UUID id;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String image;
    private String spouseName;
    private String fatherName;
    private String motherName;
    private String email;
    private String phoneNumber;
    private String gender;
    private LocalDate dob;
    private String nid;
}
