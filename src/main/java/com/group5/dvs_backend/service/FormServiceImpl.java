package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Form;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.enums.FormEnum;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.FormRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class FormServiceImpl implements FormService{

    private FormRepository formRepository;
    private ValuationRequestRepository valuationRequestRepository;
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Override
    public Form createForm(Form form) {
        return formRepository.save(form);
    }

    public Form createReceipt(List<ValuationRequestDetail> valuationRequestDetails, Long valuationRequestId) {
        if (valuationRequestDetails.isEmpty()) {
            throw new IllegalArgumentException("The list of ValuationRequestDetails cannot be empty.");
        }
        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(valuationRequestId)
                .orElseThrow(() -> new RuntimeException("No Valuation Request Found"));

        for (ValuationRequestDetail detail : valuationRequestDetails) {
            ValuationRequestDetail existingDetail = valuationRequestDetailRepository.findById(detail.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "ValuationRequestDetail not found for id: " + detail.getId()));
            existingDetail.setSize(detail.getSize());
            existingDetail.setStatus("FILLING");
            valuationRequestDetailRepository.save(existingDetail);
        }

        Form form = formRepository.save(new Form(valuationRequestId, FormEnum.RECEIPT.name(), "", new Date(), "ACCEPTED"));

        valuationRequest.setStatus("RECEIVED");
        valuationRequestRepository.save(valuationRequest);
        return form;
    }
}
