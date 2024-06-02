package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@AllArgsConstructor
public class StaffController {
    private StaffService service;
    @GetMapping("/{id}")
    public ResponseEntity<Staff> findStaffById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getStaffById(id));
    }

    @GetMapping("/staffs")
    public List<Staff> getAll(){
        return service.getAll();
    }

    @GetMapping("/valuation-staffs")
    public List<Staff> getValuationStaffs(){
        return service.getStaffByRole("VALUATION_STAFF");
    }
}
