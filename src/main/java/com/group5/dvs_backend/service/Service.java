package com.group5.dvs_backend.service;

import java.util.List;

public interface Service {
    com.group5.dvs_backend.entity.Service getById(Long id);
    List<com.group5.dvs_backend.entity.Service> getAll();
    com.group5.dvs_backend.entity.Service updateService(Long id, Service updatedService);


    com.group5.dvs_backend.entity.Service updateService(Long id, com.group5.dvs_backend.entity.Service updatedService);
}
