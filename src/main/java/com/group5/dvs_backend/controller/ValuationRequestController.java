package com.group5.dvs_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.service.ValuationRequestService;

@RestController
@RequestMapping("/api/request")
@CrossOrigin
public class ValuationRequestController {

    private final ValuationRequestService valuationRequestService;

    public ValuationRequestController(ValuationRequestService valuationRequestService) {
        this.valuationRequestService = valuationRequestService;
    }

    @PostMapping("/create")
    public void HandlerValuationRequestInfor(@RequestBody ValuationRequest valuationRequest) {
        this.valuationRequestService.saveValuationRequestInfor(valuationRequest);
    }

    @GetMapping("/waiting")
    public List<ValuationRequest> getWaitingRequests() {
        return valuationRequestService.getDetailedWaitingRequest();
    }

    @PutMapping("/{requestId}/assign/{consultingStaffId}")
    public void assignConsultingStaff(@PathVariable Long requestId, @PathVariable Long consultingStaffId) {
        valuationRequestService.assignConsultingStaff(requestId, consultingStaffId);
    }

    @PostMapping("/create-receipt/{id}")
    public void createReceipt(@RequestBody List<ValuationRequestDetail> details, @PathVariable("id") Long valuationRequestId) {
        valuationRequestService.createReceipt(details, valuationRequestId);
    }

    @GetMapping("/valuation-request/{id}")
    public ResponseEntity<ValuationRequest> getValuationRequestById(@PathVariable Long id) {
       return ResponseEntity.ok(valuationRequestService.findById(id));
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelRequest(@PathVariable("id") Long id){
        valuationRequestService.cancelRequest(id);
    }

    @GetMapping("/valuation-request/{staffId}/{status}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByStatusAndStaffId(@PathVariable("staffId") Long id,
                                                                                        @PathVariable("status") String status){
        return ResponseEntity.ok(valuationRequestService.getAcceptedRequestsByConsultingStaffId(id, status));
    }
}
