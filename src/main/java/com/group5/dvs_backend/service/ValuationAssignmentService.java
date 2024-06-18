package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.entity.ValuationAssignment;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.StaffRepository;
import com.group5.dvs_backend.repository.ValuationAssignmentRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ValuationAssignmentService {

    @Autowired
    private final ValuationAssignmentRepository valuationAssignmentRepository;
    @Autowired
    private final StaffRepository staffRepository;
    @Autowired
    private final ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Autowired
    private final ValuationRequestRepository valuationRequestRepository;

//    public ValuationAssignmentService(ValuationAssignmentRepository valuationAssignmentRepository,
//            StaffRepository staffRepository, ValuationRequestDetailRepository valuationRequestDetailRepository) {
//        this.valuationAssignmentRepository = valuationAssignmentRepository;
//        this.staffRepository = staffRepository;
//        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
//    }

//    @Transactional
//    public String assignStaffForValuation(ValuationRequestDetail valuationRequestDetail) {
//        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
//                .findById(valuationRequestDetailId)
//                .orElseThrow(() -> new RuntimeException("ValuationRequestDetail not found"));
//
//        assignStaff(staff1Id, valuationRequestDetail);
//        assignStaff(staff2Id, valuationRequestDetail);
//        assignStaff(staff3Id, valuationRequestDetail);
//        return "Assign Successfully";
//    }
//
//    private void assignStaff(Long staffId, ValuationRequestDetail valuationRequestDetail) {
//        Staff staff = staffRepository.findById(staffId)
//                .orElseThrow(() -> new RuntimeException("Staff not found"));
//
//        ValuationAssignment valuationAssignment = new ValuationAssignment();
//        valuationAssignment.setValuationStaff(staff);
//        valuationAssignment.setValuationRequestDetail(valuationRequestDetail);
//        valuationAssignment.setStatus("ASSIGNED");
//
//        valuationAssignmentRepository.save(valuationAssignment);
//    }

    public String assignStaffs(List<Staff> list, Long valuationDetailId, Long requestId){
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(valuationDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation Request Detail not found"));
        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Valuation Request not found"));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(valuationRequest.getReceivingDate());
        calendar.add(Calendar.HOUR_OF_DAY, -1);

        Date deadline = calendar.getTime();

        List<ValuationAssignment> valuationAssignments = new ArrayList<>();
        for(Staff staff : list){
            valuationAssignments.add(new ValuationAssignment(staff, valuationDetailId,"ASSIGNED", deadline));
        }

        valuationRequestDetail.setAssignmentList(valuationAssignments);
        valuationRequestDetail.setStatus("ASSIGNED");

        valuationRequestDetailRepository.save(valuationRequestDetail);

        return "Assign Successfully";
    }

    public List<ValuationAssignment> findAll(){
        return valuationAssignmentRepository.findAll();
    }

    public List<ValuationAssignment> findByStaffId(Long id){
        List<ValuationAssignment> valuationAssignments = valuationAssignmentRepository.findByValuationStaffId(id);
        for (ValuationAssignment valuationAssignment : valuationAssignments){
            ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                    .findById(valuationAssignment.getValuationRequestDetailId())
                    .orElseThrow(() -> new ResourceNotFoundException("No Valuation Request Detail Found"));
            valuationAssignment.setValuationRequestDetailId(valuationRequestDetail.getId());
        }
        return valuationAssignments;
    }

    public ValuationAssignment valuate(ValuationAssignment valuationAssignment){
        valuationAssignment.setStatus("VALUATED");
        System.out.println("Set Status");
        ValuationAssignment updatedAssignment = valuationAssignmentRepository.save(valuationAssignment);
        System.out.println("Save Database");
        List<ValuationAssignment> list = valuationAssignmentRepository
                .findByValuationRequestDetailId(updatedAssignment.getValuationRequestDetailId());

        boolean check = true;

        for (ValuationAssignment assignment : list){
            if (assignment.getStatus().equals("ASSIGNED")){
                check = false;
                break;
            }
        }

        if (check){
            ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                    .findById(updatedAssignment.getValuationRequestDetailId())
                    .orElseThrow(() -> new ResourceNotFoundException("No Valuation Request Detail found"));

            valuationRequestDetail.setStatus("VALUATED");
            valuationRequestDetailRepository.save(valuationRequestDetail);
        }

        return updatedAssignment;
    }
}