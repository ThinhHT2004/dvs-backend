package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("SELECT s FROM Staff s JOIN s.account WHERE s.account.role =?1")
    List<Staff> findByRole(String role);
}
