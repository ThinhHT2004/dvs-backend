package com.group5.dvs_backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;

public interface FileService {
    public String readFile(MultipartFile file) throws IOException, ParseException;
}
