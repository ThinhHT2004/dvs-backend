package com.group5.dvs_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.group5.dvs_backend.entity.ValuationRequest;
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
        return valuationRequestService.getRequestsByStatus("Waiting");
    }

    @PutMapping("/{requestId}/assign/{consultingStaffId}")
    public void assignConsultingStaff(@PathVariable Long requestId, @PathVariable Long consultingStaffId) {
        valuationRequestService.assignConsultingStaff(requestId, consultingStaffId);
    }

}
