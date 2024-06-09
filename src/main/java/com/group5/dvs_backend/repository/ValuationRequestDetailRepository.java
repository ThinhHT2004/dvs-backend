package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValuationRequestDetailRepository extends JpaRepository<ValuationRequestDetail, Long> {
    List<ValuationRequestDetail> findValuationRequestDetailByStatus(String status);

    @Query("SELECT vrd  FROM ValuationRequestDetail vrd WHERE vrd.valuationRequestId = ?1")
    List<ValuationRequestDetail> findValuationRequestDetailByValuationRequestId(Long id);
}
