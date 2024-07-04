package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Service;
import com.group5.dvs_backend.entity.ServicePrice;
import com.group5.dvs_backend.service.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/update")
    public ResponseEntity<Service> updateService(@RequestBody Service updatedService) {
        return ResponseEntity.ok(serviceImpl.updateService(updatedService));
    }

    @PostMapping("/create")
    public ResponseEntity<Service> createService(@RequestBody Service service){
        return ResponseEntity.ok(serviceImpl.create(service));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable("id") Long id){
        return ResponseEntity.ok(serviceImpl.delete(id));
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<String> disableService(@PathVariable("id") Long id){
        return ResponseEntity.ok(serviceImpl.disable(id));
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<String> enableService(@PathVariable("id") Long id){
        return ResponseEntity.ok(serviceImpl.enable(id));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Service>> getActiveService(){
        return ResponseEntity.of(Optional.ofNullable(serviceImpl.getActiveService()));
    }
}
