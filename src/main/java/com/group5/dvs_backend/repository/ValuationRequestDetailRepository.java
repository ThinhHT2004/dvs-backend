package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.ValuationRequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValuationRequestDetailRepository extends JpaRepository<ValuationRequestDetail, Long> {
}
