package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
@CrossOrigin
public class FileController {

    private FileService fileService;
    @PostMapping("/read")
    public ResponseEntity<String> readFile(@RequestParam("file")MultipartFile file) throws IOException, ParseException {
        return ResponseEntity.ok(fileService.readFile(file));
    }
}
