package com.group5.dvs_backend.service;

import java.util.Collections;
import java.util.List;

import com.group5.dvs_backend.entity.ValuationReport;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.ValuationReportRepository;
import com.group5.dvs_backend.repository.ValuationRequestDetailRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ValuationRequestService {
    @Autowired
    private final ValuationRequestRepository valuationRequestRepository;
    @Autowired
    private final ValuationRequestDetailRepository valuationRequestDetailRepository;
    @Autowired
    private final ValuationReportRepository valuationReportRepository;

    public List<ValuationRequest> getAll() {
        return valuationRequestRepository.findAll();
    }

    public List<ValuationRequest> getByConsultingStaffId(Long id) {
        return valuationRequestRepository.findAllByConsultingStaffId(id);
    }

    public void saveValuationRequestInfor(ValuationRequest valuationRequest) {
        ValuationRequest savedValuationRequest = valuationRequestRepository.save(valuationRequest);
        for (int i = 0; i < savedValuationRequest.getQuantity(); i++) {
            ValuationReport valuationReport = valuationReportRepository.save(new ValuationReport());
            ValuationRequestDetail valuationRequestDetail = valuationRequestDetailRepository
                    .save(new ValuationRequestDetail(
                            savedValuationRequest, valuationReport, "WAITING", 0.0, false));
        }
    }

    public List<ValuationRequest> getRequestsByStatus(String status) {
        return valuationRequestRepository.findByStatus(status);
    }

    public List<ValuationRequest> getDetailedWaitingRequest() {
        return valuationRequestRepository.findWaitingRequestWithDetails();
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



    // Tìm những requets có consulting staff id được truyền vào
    public List<ValuationRequest> getAcceptedRequestsByConsultingStaffId(Long consultingStaffId) {
        return valuationRequestRepository.findByConsultingStaffIdAndStatus(consultingStaffId, "ACCEPTED");
    }

    // tao bien lai
    public void createReceipt(List<ValuationRequestDetail> valuationRequestDetails) {
        if (valuationRequestDetails.isEmpty()) {
            throw new IllegalArgumentException("The list of ValuationRequestDetails cannot be empty.");
        }
        ValuationRequest valuationRequest = valuationRequestDetails.get(0).getValuationRequest();

        for (ValuationRequestDetail detail : valuationRequestDetails) {
            ValuationRequestDetail existingDetail = valuationRequestDetailRepository.findById(detail.getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "ValuationRequestDetail not found for id: " + detail.getId()));
            existingDetail.setSize(detail.getSize());
            existingDetail.setStatus("FILLING");
            valuationRequestDetailRepository.save(existingDetail);
        }

        valuationRequest.setStatus("RECEIVED");
        valuationRequestRepository.save(valuationRequest);
    }

}
