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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class FormServiceImpl implements FormService{

    @Autowired
    private FormRepository formRepository;
    @Autowired
    private ValuationRequestRepository valuationRequestRepository;
    @Autowired
    private ValuationRequestDetailRepository valuationRequestDetailRepository;

    @Override
    public Form createForm(Form form) {
        Form updatedForm = new Form();
        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(form.getValuationRequestId())
                .orElseThrow(() -> new RuntimeException("No Valuation Request Found"));
        form.setCreatedDate(new Date());
        if (form.getFormType().equals("HAND OVER")){
            form.setFormType("HAND-OVER");
            form.setStatus("ACCEPTED");
            updatedForm = formRepository.save(form);
            valuationRequest.setStatus("FINISHED");
            valuationRequestRepository.save(valuationRequest);
        }else {
            if (form.getFormType().equals("SEALED")){
                form.setFormType(FormEnum.SEALED_FORM.name());
                form.setStatus("WAITING");
                updatedForm = formRepository.save(form);
            }else{
                form.setFormType(FormEnum.COMMITMENT_FORM.name());
                form.setStatus("WAITING");
                updatedForm = formRepository.save(form);
            }
        }
        return updatedForm;
    }

    public Form createReceipt(List<ValuationRequestDetail> valuationRequestDetails, Long valuationRequestId, Long total) {
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

        Form form = formRepository.save(new Form(valuationRequestId, FormEnum.RECEIPT.name(), String.valueOf(total), new Date(), "ACCEPTED"));

        valuationRequest.setStatus("RECEIVED");
        valuationRequestRepository.save(valuationRequest);
        return form;
    }

    @Override
    public List<Form> getWaitingForms() {
        return formRepository.findByWaitingStatus();
    }

    @Override
    public Form approveForm(Long formId) {

        Form form = formRepository.findById(formId).orElseThrow(() -> new ResourceNotFoundException("Form Not Found"));
        form.setStatus("ACCEPTED");
        if (form.getFormType().equals("SEALED_FORM")){
            ValuationRequest valuationRequest = valuationRequestRepository
                    .findById(form.getValuationRequestId())
                    .orElseThrow(() -> new RuntimeException("No Valuation Request Found"));
            valuationRequest.setStatus("SEALED");
            valuationRequestRepository.save(valuationRequest);
        }

        return formRepository.save(form);
    }

    @Override
    public Form denyForm(Long formId) {
        Form form = formRepository.findById(formId).orElseThrow(() -> new ResourceNotFoundException("Form Not Found"));

        form.setStatus("DENIED");
        return formRepository.save(form);
    }


}
