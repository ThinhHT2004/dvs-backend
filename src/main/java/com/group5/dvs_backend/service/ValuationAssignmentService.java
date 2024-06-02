package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.entity.ValuationAssignment;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.repository.StaffRepository;
import com.group5.dvs_backend.repository.ValuationAssignmentRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ValuationAssignmentService {

    private final ValuationAssignmentRepository valuationAssignmentRepository;
    private final StaffRepository staffRepository;
    private final ValuationRequestDetailRepository valuationRequestDetailRepository;

    public ValuationAssignmentService(ValuationAssignmentRepository valuationAssignmentRepository,
            StaffRepository staffRepository, ValuationRequestDetailRepository valuationRequestDetailRepository) {
        this.valuationAssignmentRepository = valuationAssignmentRepository;
        this.staffRepository = staffRepository;
        this.valuationRequestDetailRepository = valuationRequestDetailRepository;
    }

    @Transactional
    public void assignStaffForValuation(Long staff1Id, Long staff2Id, Long staff3Id, Long valuationRequestDetailId) {
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(valuationRequestDetailId)
                .orElseThrow(() -> new RuntimeException("ValuationRequestDetail not found"));

        assignStaff(staff1Id, valuationRequestDetail);
        assignStaff(staff2Id, valuationRequestDetail);
        assignStaff(staff3Id, valuationRequestDetail);
    }

    private void assignStaff(Long staffId, ValuationRequestDetail valuationRequestDetail) {
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        ValuationAssignment valuationAssignment = new ValuationAssignment();
        valuationAssignment.setValuationStaff(staff);
        valuationAssignment.setValuationRequestDetail(valuationRequestDetail);
        valuationAssignment.setStatus("ASSIGNED");

        valuationAssignmentRepository.save(valuationAssignment);
    }
}