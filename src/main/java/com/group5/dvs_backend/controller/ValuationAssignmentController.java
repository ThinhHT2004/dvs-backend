package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Service;
import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.entity.ValuationAssignment;
import com.group5.dvs_backend.entity.ValuationRequest;
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

    @PutMapping("/assign/{requestId}/{id}")
    public String assignStaffs(@RequestBody List<Staff> staffs,
                               @PathVariable("id") Long id,
                               @PathVariable("requestId") Long requestId){

        return valuationAssignmentService.assignStaffs(staffs, id, requestId);
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
        System.out.println("Call Api");
        return valuationAssignmentService.valuate(valuationAssignment);
    }

}
