package com.group5.dvs_backend.service;

import java.util.List;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequest.Status;
import com.group5.dvs_backend.repository.ValuationRequestRepository;

public class ValuationRequestService {
    private final ValuationRequestRepository valuationRequestRepository;

    public ValuationRequestService(ValuationRequestRepository valuationRequestRepository) {
        this.valuationRequestRepository = valuationRequestRepository;
    }

    public List<ValuationRequest> getAll() {
        return valuationRequestRepository.findAll();
    }

    public List<ValuationRequest> getByConsultingStaffId(Long id) {
        return valuationRequestRepository.findAllByConsultingStaffId(id);
    }

    public void saveValuationRequestInfor(ValuationRequest valuationRequest) {
        valuationRequest.setStatus(Status.WAITING);
        this.valuationRequestRepository.save(valuationRequest);
    }

}
