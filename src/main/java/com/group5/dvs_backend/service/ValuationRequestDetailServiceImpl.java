package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ValuationRequestDetailServiceImpl implements ValuationRequestDetailService{

    @Autowired
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Autowired
    private ValuationRequestRepository valuationRequestRepository;
    @Override
    public List<ValuationRequestDetail> getValuationRequestDetailByStatus(String status) {

        return valuationRequestDetailRepository.findValuationRequestDetailByStatus(status);
    }

    @Override
    public ValuationRequestDetail updateRequest(ValuationRequestDetail valuationRequestDetail) {
        boolean check = true;

        valuationRequestDetail.setStatus("APPROVED");


        ValuationRequestDetail updated = valuationRequestDetailRepository.save(valuationRequestDetail);

        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(valuationRequestDetail.getValuationRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("No Valuation Request Found"));
        List<ValuationRequestDetail> valuationRequestDetails = valuationRequestDetailRepository
                .findValuationRequestDetailByValuationRequestId(valuationRequest.getId());

        for (ValuationRequestDetail detail : valuationRequestDetails){
            if (!detail.getStatus().equals("APPROVED")){
                check = false;
                break;
            }
        }

        if (check){
            valuationRequest.setStatus("COMPLETED");
        }

        valuationRequestRepository.save(valuationRequest);

        return updated;
    }
}
