package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.Staff;
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

    @GetMapping("/valuation-staffs")
    public List<Staff> getValuationStaffs(){
        return service.getStaffByRole("VALUATION_STAFF");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(@PathVariable Long id, @RequestBody Staff staff) {
        staff.setId(id);
        Staff updatedStaff = service.updateStaff(staff);
        return ResponseEntity.ok(updatedStaff);
    }
}
