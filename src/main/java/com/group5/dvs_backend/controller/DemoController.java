package com.group5.dvs_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/sayhi")
    public ResponseEntity<String> sayHi(){
        System.out.println("Hello");
        return ResponseEntity.ok("Hi");
    }
}
