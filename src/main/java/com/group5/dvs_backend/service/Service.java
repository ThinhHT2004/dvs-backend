package com.group5.dvs_backend.service;

import org.springframework.http.ProblemDetail;

import java.util.List;

public interface Service {
    com.group5.dvs_backend.entity.Service getById(Long id);
    List<com.group5.dvs_backend.entity.Service> getAll();


    com.group5.dvs_backend.entity.Service updateService(com.group5.dvs_backend.entity.Service updatedService);

    com.group5.dvs_backend.entity.Service create(com.group5.dvs_backend.entity.Service service);

    String delete(Long id);

    String disable(Long id);

    List<com.group5.dvs_backend.entity.Service> getActiveService();

    String enable(Long id);
}
