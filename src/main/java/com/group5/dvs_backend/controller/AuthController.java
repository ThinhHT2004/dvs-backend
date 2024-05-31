package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Account signup(@RequestParam String username, @RequestParam String password, @RequestParam String confirmPassword) {
        return userService.registerUser(username, password, confirmPassword);
    }

    @PostMapping("/login")
    public Account login(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }


}
