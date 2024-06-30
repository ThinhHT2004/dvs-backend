package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Staff;

import java.util.List;

public interface StaffService {
    List<Staff> getAll();
    Staff getStaffById(Long id);
    Staff updateStaff(Staff staff);
    List<Staff> getStaffByRole(String role);
    Staff updateStaff̣̣̣̣(Staff updatedStaff);

}
