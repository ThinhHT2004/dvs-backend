package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.entity.Auth;
import com.group5.dvs_backend.entity.AuthResponse;
import com.group5.dvs_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Account signup(@RequestBody Auth auth) {
        return userService.registerUser(auth);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody Auth auth) {
        return userService.loginUser(auth);
    }

    @GetMapping("/login/google")
    public String googleLogin() {
        return "redirect:/oauth2/authorization/google";
    }

}
