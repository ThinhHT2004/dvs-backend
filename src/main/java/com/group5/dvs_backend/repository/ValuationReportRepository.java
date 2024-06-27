package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValuationReportRepository extends JpaRepository<ValuationReport, Long> {
    @Query("SELECT va FROM ValuationReport va WHERE va.id = ?1")
    List<ValuationReport> findDiamondByValuationReportId(Long id);

    @Query("SELECT va FROM ValuationReport va WHERE va.labId = ?1 AND va.active = true")
    Optional<ValuationReport> findByLabId(String id);
}
