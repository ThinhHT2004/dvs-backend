package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.*;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.payload.RegisterResponse;
import com.group5.dvs_backend.payload.RegisterStaffRequest;
import com.group5.dvs_backend.repository.AccountRepository;
import com.group5.dvs_backend.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StaffServiceImpl implements StaffService{
    private StaffRepository staffRepository;
    private AccountRepository accountRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
    public List<Staff> getStaffByRole(String role){
        return staffRepository.findByRole(role);
    }

    @Override
    public Staff updateStaff(UpdateRequest request) {
        Optional<Staff> existingStaffOpt =staffRepository.findById(request.getId());

        if(existingStaffOpt.isPresent()){
            Staff existingStaff= existingStaffOpt.get();
            existingStaff.setFirstName(request.getFirstName());
            existingStaff.setLastName(request.getLastName());
            existingStaff.setEmail(request.getEmail());
            existingStaff.setPhoneNumber(request.getPhoneNumber());
            existingStaff.setAddress(request.getAddress());
            existingStaff.setDob(request.getDob());
            Account account = existingStaff.getAccount();
            account.setRole(request.getRole());
            existingStaff.setAccount(account);

            return staffRepository.save(existingStaff);
        }else{
            throw new RuntimeException("Staff not found with id "+ request.getId());
        }

    }

    @Override
    public RegisterResponse create(RegisterStaffRequest request) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setMess("Create Staff Successfully");

        if (accountRepository.findByUsername(request.getUsername()).isPresent()){
            registerResponse.setMess("Username already exist");
            registerResponse.setCode(0L);
            return registerResponse;
        }

        if (request.getDob().after(new Date())){
            registerResponse.setMess("Date of Birth must before Today");
            registerResponse.setCode(0L);
            return registerResponse;
        }

        Account account = Account
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        Account savedAccount = accountRepository.save(account);

        Staff staff = Staff
                .builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .dob(request.getDob())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
        staff.setId(savedAccount.getId());

        staffRepository.save(staff);

        return registerResponse;
    }


}
