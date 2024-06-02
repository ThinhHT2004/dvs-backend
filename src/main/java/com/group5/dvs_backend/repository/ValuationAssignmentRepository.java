package com.group5.dvs_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.group5.dvs_backend.entity.ValuationAssignment;

public interface ValuationAssignmentRepository extends JpaRepository<ValuationAssignment, Long> {
}