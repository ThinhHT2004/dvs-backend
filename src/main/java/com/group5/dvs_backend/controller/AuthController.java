package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.*;
import com.group5.dvs_backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
            ){
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
            ){
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/confirm/{type}/{token}")
    public ResponseEntity<ConfirmationTokenResponse> confirm(@PathVariable("token") String token, @PathVariable("type") String type) throws IOException {
         return ResponseEntity.ok(userService.confirmToken(token, type));
    }




//    @GetMapping("/login/google")
//    public void googleLogin(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/oauth2/authorization/google");
//    }

}
