package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.payload.RegisterResponse;
import com.group5.dvs_backend.payload.RegisterStaffRequest;
import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.entity.UpdateRequest;
import com.group5.dvs_backend.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staffs")
@AllArgsConstructor
@CrossOrigin
public class StaffController {
    private StaffService service;
    @GetMapping("/{id}")
    public ResponseEntity<Staff> findStaffById(@PathVariable("id") Long id){
        return ResponseEntity.ok(service.getStaffById(id));
    }

    @GetMapping("/")
    public List<Staff> getAll(){
        return service.getAll();
    }

    @GetMapping("/valuation-staffs/active")
    public List<Staff> getValuationStaffs(){
        return service.getStaffByRole("VALUATION_STAFF");
    }

    @PutMapping("/update")
    public ResponseEntity<Staff> updateStaff(@RequestBody UpdateRequest request) {
        Staff updatedStaff = service.updateStaff(request);
        return ResponseEntity.ok(updatedStaff);
    }

    @PostMapping("/create")
    public ResponseEntity<RegisterResponse> createStaff(@RequestBody RegisterStaffRequest request){
        return ResponseEntity.ok(service.create(request));
    }
}
