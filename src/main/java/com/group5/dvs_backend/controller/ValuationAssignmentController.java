package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.entity.ValuationAssignment;
import com.group5.dvs_backend.service.ValuationAssignmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assignment")
@AllArgsConstructor
@CrossOrigin
public class ValuationAssignmentController {

    private ValuationAssignmentService valuationAssignmentService;

    @PutMapping("/assign/{id}")
    public String assignStaffs(@RequestBody List<Staff> staffs, @PathVariable("id") Long id){
        return valuationAssignmentService.assignStaffs(staffs, id);
    }

    @GetMapping("/all")
    public List<ValuationAssignment> getAll(){
        return valuationAssignmentService.findAll();
    }

    @GetMapping("/ASSIGNED/{id}")
    public List<ValuationAssignment> getById(@PathVariable("id") Long id){
        return valuationAssignmentService.findByStaffId(id);
    }

    @PutMapping("/update")
    public ValuationAssignment valuateSample(@RequestBody ValuationAssignment valuationAssignment){
        return valuationAssignmentService.valuate(valuationAssignment);
    }

}
