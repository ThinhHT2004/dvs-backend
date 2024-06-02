package com.group5.dvs_backend.service;

import java.util.Collections;
import java.util.List;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import org.springframework.stereotype.Service;

@Service
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
        this.valuationRequestRepository.save(valuationRequest);
    }

    public List<ValuationRequest> getRequestsByStatus(String status) {
        return valuationRequestRepository.findByStatus(status);
    }

    public void assignConsultingStaff(Long requestId, Long consultingStaffId) {
        ValuationRequest valuationRequest = valuationRequestRepository.findById(requestId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Valuation Request not found for this id :: " + requestId));

        if ("Waiting".equalsIgnoreCase(valuationRequest.getStatus())) {
            valuationRequest.setConsultingStaffId(consultingStaffId);
            valuationRequest.setStatus("APPROVED");
            valuationRequestRepository.save(valuationRequest);
        } else {
            throw new IllegalStateException("Request is not in 'Waiting' state");
        }
    }
    public List<ValuationRequest> findById (Long id){
        return valuationRequestRepository.findById(id)
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }



}
