package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("SELECT f FROM Form f WHERE f.status = 'WAITING'")
    List<Form> findByWaitingStatus();

    @Query("SELECT f FROM Form f WHERE f.formType = 'RECEIPT'")
    List<Form> findByReceipt();

    @Query("SELECT f FROM Form f " +
            "WHERE f.createdDate >= :from AND f.createdDate <= :to AND f.formType = 'RECEIPT'")
    List<Form> findByReceiptsInRange(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
