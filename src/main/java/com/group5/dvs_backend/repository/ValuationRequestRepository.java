package com.group5.dvs_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group5.dvs_backend.entity.ValuationRequest;

@Repository
public interface ValuationRequestRepository extends JpaRepository<ValuationRequest, Long> {
    List<ValuationRequest> findAllByConsultingStaffId(Long id);

    List<ValuationRequest> findByStatus(String status);
}
