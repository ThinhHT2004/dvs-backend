package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.*;
import com.group5.dvs_backend.payload.*;
import com.group5.dvs_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ConfirmationTokenResponse> confirm(@PathVariable("token") String token, @PathVariable("type") String type){
         return ResponseEntity.ok(userService.confirmToken(token, type));
    }

    @GetMapping("/send-verification/{username}")
    public ResponseEntity<Long> sendVerification(@PathVariable("username") String username){
        return ResponseEntity.ok(userService.sendVerification(username));
    }

    @PutMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(userService.changePassword(request));
    }




//    @GetMapping("/login/google")
//    public void googleLogin(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/oauth2/authorization/google");
//    }

}
