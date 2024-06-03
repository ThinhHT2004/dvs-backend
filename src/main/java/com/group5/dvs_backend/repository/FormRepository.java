package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormRepository extends JpaRepository<Form, Long> {
}
