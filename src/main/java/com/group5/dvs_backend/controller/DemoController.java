package com.group5.dvs_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group5.dvs_backend.entity.EmailDetail;
import com.group5.dvs_backend.service.EmailService;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired
    private EmailService emailService;

    @GetMapping("/sayhi")
    public ResponseEntity<String> sayHi() {
        System.out.println("Hello");
        return ResponseEntity.ok("Hi");
    }

    @GetMapping("/test/send-mail")
    public void getMethodName(@RequestParam String email) {
        EmailDetail emailDetail = new EmailDetail();
        // fix cứng để test
        emailDetail.setRecipient("dovantri1709@gmail.com");
        emailDetail.setSubject("Thank you for registering.");
        emailDetail.setName("tri");
        // template name la ten template trong folder resources/templates
        emailService.sendMailTemplate(emailDetail, "emailtemplate");
    }

}
