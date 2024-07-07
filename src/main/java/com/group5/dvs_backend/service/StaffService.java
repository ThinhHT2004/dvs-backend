package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.RegisterResponse;
import com.group5.dvs_backend.entity.RegisterStaffRequest;
import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.entity.UpdateRequest;

import java.util.List;

public interface StaffService {
    List<Staff> getAll();
    Staff getStaffById(Long id);
    List<Staff> getStaffByRole(String role);
    Staff updateStaff(UpdateRequest request);


    RegisterResponse create(RegisterStaffRequest request);
}
