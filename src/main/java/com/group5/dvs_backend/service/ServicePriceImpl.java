package com.group5.dvs_backend.service;

import com.group5.dvs_backend.repository.ServicePriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServicePriceImpl implements ServicePrice{
    private ServicePriceRepository servicePriceRepository;
    @Override
    public Double findPriceByIdAndSize(Long serviceID, Double size) {
        return servicePriceRepository.findPriceByIdAndSize(serviceID, size);
    }
}
