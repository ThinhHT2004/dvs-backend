package com.group5.dvs_backend.service;

import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceImpl implements com.group5.dvs_backend.service.Service {

    private ServiceRepository serviceRepository;

    @Override
    public com.group5.dvs_backend.entity.Service getById(Long id) {
        return serviceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Service Found"));
    }

    @Override
    public List<com.group5.dvs_backend.entity.Service> getAll() {
        return serviceRepository.findAll();
    }
}
