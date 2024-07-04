package com.group5.dvs_backend.service;

import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
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



    @Override
    public com.group5.dvs_backend.entity.Service updateService(com.group5.dvs_backend.entity.Service updatedService) {
        updatedService.setUpdatedDate(new Date());

        return serviceRepository.save(updatedService);
    }

    @Override
    public com.group5.dvs_backend.entity.Service create(com.group5.dvs_backend.entity.Service service) {
        service.setUpdatedDate(new Date());
        return serviceRepository.save(service);
    }

    @Override
    public String delete(Long id) {
        serviceRepository.deleteById(id);
        return "Service Deleted";
    }

    @Override
    public String disable(Long id) {
        com.group5.dvs_backend.entity.Service service = serviceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Service Found"));
        service.setActive(false);
        serviceRepository.save(service);
        return "Disable Service Successfully";
    }

    @Override
    public List<com.group5.dvs_backend.entity.Service> getActiveService() {
        return serviceRepository.getActiveService();
    }

    @Override
    public String enable(Long id) {
        com.group5.dvs_backend.entity.Service service = serviceRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Service Found"));
        service.setActive(true);
        serviceRepository.save(service);
        return "Enable Service Successfully";
    }


}
