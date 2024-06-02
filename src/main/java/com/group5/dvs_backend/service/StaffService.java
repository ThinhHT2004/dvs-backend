package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffService {
    List<Staff> getAll();
    Staff getStaffById(Long id);
    void updateStaff(Staff staff);
    List<Staff> getStaffByRole(String role);
}
