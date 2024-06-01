package com.group5.dvs_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Auth {
    private String username;
    private String password;
    private String confirmPassword;
}
