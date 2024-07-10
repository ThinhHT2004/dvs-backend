package com.group5.dvs_backend.entity;

import org.springframework.core.io.FileSystemResource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    String recipient;
    private String msgBody;
    private String subject;
    private String link;
    private FileSystemResource attachment;
    private String name;
}