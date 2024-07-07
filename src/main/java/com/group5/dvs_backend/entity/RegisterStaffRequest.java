package com.group5.dvs_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterStaffRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Date dob;
    private String phoneNumber;
    private String role;
    private String address;
}