package com.group5.dvs_backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.group5.dvs_backend.service.CloudinaryService;

@RestController
@RequestMapping("/api/cloudinary")
@CrossOrigin
public class CloudinaryImageUploadController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryImageUploadController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping("/upload")
    @CrossOrigin
    public ResponseEntity<List<Map>> uploadImage(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2, @RequestParam MultipartFile file3,@RequestParam Long valuationReportId) {
        List<Map> data = this.cloudinaryService.uploadToCloudinary(file1, file2, file3,valuationReportId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
