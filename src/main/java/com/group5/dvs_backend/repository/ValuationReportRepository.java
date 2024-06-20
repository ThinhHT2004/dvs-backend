package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.ValuationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValuationReportRepository extends JpaRepository<ValuationReport, Long> {
    @Query("SELECT va FROM ValuationReport va WHERE va.id = ?1")
    List<ValuationReport> findDiamondByValuationReportId(Long id);

}
