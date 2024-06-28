package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("SELECT f FROM Form f WHERE f.status = 'WAITING'")
    List<Form> findByWaitingStatus();
}
