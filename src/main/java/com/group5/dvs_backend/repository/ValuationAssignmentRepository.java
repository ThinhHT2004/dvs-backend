package com.group5.dvs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group5.dvs_backend.entity.ValuationAssignment;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ValuationAssignmentRepository extends JpaRepository<ValuationAssignment, Long> {

    @Query("SELECT va FROM ValuationAssignment va WHERE va.valuationStaff.id = ?1 AND va.status != 'APPROVED'")
    List<ValuationAssignment> findByValuationStaffId(Long id);

    @Query("SELECT va FROM ValuationAssignment va WHERE va.valuationRequestDetail.id = ?1")
    List<ValuationAssignment> findByValuationRequestDetailId(Long id);
}