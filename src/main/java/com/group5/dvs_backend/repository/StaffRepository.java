package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    List<Staff> findByRole(String role);
}
