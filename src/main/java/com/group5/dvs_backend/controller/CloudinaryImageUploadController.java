package com.group5.dvs_backend.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.group5.dvs_backend.service.CloudinaryService;

@RestController
@RequestMapping("/cloudinary")
public class CloudinaryImageUploadController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryImageUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map> uploadImage(@RequestParam("image") MultipartFile file) {
        Map data = this.cloudinaryService.upload(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
