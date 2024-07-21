package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.EmailDetail;
import com.group5.dvs_backend.entity.ValuationAssignment;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ValuationAssignmentRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ValuationRequestDetailServiceImpl implements ValuationRequestDetailService{

    @Autowired
    private ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Autowired
    private ValuationRequestRepository valuationRequestRepository;
    @Autowired
    private ValuationAssignmentRepository valuationAssignmentRepository;
    @Autowired
    private EmailService emailService;

    @Override
    public List<ValuationRequestDetail> getValuationRequestDetailByStatus(String status) {

        return valuationRequestDetailRepository.findValuationRequestDetailByStatus(status);
    }

    @Override
    public ValuationRequestDetail updateRequest(ValuationRequestDetail valuationRequestDetail) {
        boolean check = true;

        valuationRequestDetail.setStatus("APPROVED");

        ValuationRequestDetail updated = valuationRequestDetailRepository.save(valuationRequestDetail);

        for (ValuationAssignment valuationAssignment : valuationRequestDetail.getAssignmentList()){
            valuationAssignment.setStatus("APPROVED");
            valuationAssignmentRepository.save(valuationAssignment);
        }

        ValuationRequest valuationRequest = valuationRequestRepository
                .findById(valuationRequestDetail.getValuationRequestId())
                .orElseThrow(() -> new ResourceNotFoundException("No Valuation Request Found"));
        List<ValuationRequestDetail> valuationRequestDetails = valuationRequestDetailRepository
                .findValuationRequestDetailByValuationRequestId(valuationRequest.getId());

        for (ValuationRequestDetail detail : valuationRequestDetails){
            if (!detail.getStatus().equals("APPROVED") && !detail.getStatus().equals("DENIED")){
                check = false;
                break;
            }
        }

        if (check){
            valuationRequest.setStatus("COMPLETED");

            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setRecipient(valuationRequest.getCustomer().getEmail());
            emailDetail.setSubject("Diascur Completed Request Announcement");

            Context context = new Context();
            context.setVariable("firstName", valuationRequest.getCustomer().getFirst_name());
            context.setVariable("lastName", valuationRequest.getCustomer().getLast_name());
            context.setVariable("requestId", valuationRequest.getId());
            context.setVariable("receiveDate", new Date());

            emailService.sendMailTemplate(emailDetail, "CompletedRequest.html", context);
        }

        valuationRequestRepository.save(valuationRequest);

        return updated;
    }

    @Override
    public ValuationRequestDetail getById(Long id) {
        return valuationRequestDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Sample Found"));
    }

    @Override
    public ValuationRequestDetail deny(Long id) {
        ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Sample Found"));

        valuationRequestDetail.setStatus("DENIED");
        valuationRequestDetail.setDiamond(false);
        return valuationRequestDetailRepository.save(valuationRequestDetail);
    }


}
