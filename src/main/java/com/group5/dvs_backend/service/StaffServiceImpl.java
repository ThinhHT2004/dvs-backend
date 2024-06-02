package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Staff;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StaffServiceImpl implements StaffService{
    private StaffRepository staffRepository;
    @Override
    public List<Staff> getAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffById(Long id) {
        Optional<Staff> staff = staffRepository.findById(id);
        Staff foundStaff = null;
        if (staff.isPresent()){
            foundStaff = staff.get();
        }else {
            throw new ResourceNotFoundException("Staff with ID " + id + " not found");
        }
        return foundStaff;
    }

    @Override
    public void updateStaff(Staff staff) {

    }

    @Override
    public List<Staff> getStaffByRole(String role){
        return staffRepository.findByRole(role);
    }
}
