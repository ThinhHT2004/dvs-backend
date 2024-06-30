package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Service;
import com.group5.dvs_backend.service.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@AllArgsConstructor
@CrossOrigin
public class ServiceController {
    private final ServiceImpl serviceImpl;
    private com.group5.dvs_backend.service.Service service;

    @GetMapping("{id}")
    public ResponseEntity<Service> getServiceById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Service>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Service> updateService(@PathVariable Long id, @RequestBody Service updatedService) {
        try {
            Service service = serviceImpl.updateService(id, (com.group5.dvs_backend.service.Service) updatedService);
            return ResponseEntity.ok(service);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
