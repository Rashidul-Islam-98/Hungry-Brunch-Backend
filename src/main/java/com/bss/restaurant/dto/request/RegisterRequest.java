package com.bss.restaurant.dto.request;

import lombok.Getter;

import java.time.LocalDate;
@Getter
public class RegisterRequest {
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String image;
    private String spouseName;
    private String fatherName;
    private String motherName;
    private String email;
    private String phoneNumber;
    private Integer genderId;
    private LocalDate dob;
    private String nid;
    private String base64;
}
