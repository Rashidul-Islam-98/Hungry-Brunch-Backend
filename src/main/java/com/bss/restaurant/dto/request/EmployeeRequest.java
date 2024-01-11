package com.bss.restaurant.dto.request;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class EmployeeRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String image;
    private String base64;
    private String spouseName;
    private String fatherName;
    private String motherName;
    private String designation;
    private String email;
    private String phoneNumber;
    private Integer genderId;
    private LocalDate dob;
    private LocalDate joinDate;
    private String nid;
}
