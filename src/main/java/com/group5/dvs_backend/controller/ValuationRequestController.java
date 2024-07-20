package com.group5.dvs_backend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelRequest(@PathVariable Long id) {
        return ResponseEntity.ok(valuationRequestService.cancelRequest(id));
    }

    @GetMapping("/waiting")
    public List<ValuationRequest> getWaitingRequests() {
        return valuationRequestService.getDetailedWaitingRequest();
    }

    @PutMapping("/{requestId}/assign/{consultingStaffId}")
    public void assignConsultingStaff(@PathVariable Long requestId, @PathVariable Long consultingStaffId) {
        valuationRequestService.assignConsultingStaff(requestId, consultingStaffId);
    }



    @GetMapping("/valuation-request/{id}")
    public ResponseEntity<ValuationRequest> getValuationRequestById(@PathVariable Long id) {
       return ResponseEntity.ok(valuationRequestService.findById(id));
    }


    @GetMapping("/valuation-request/{staffId}/{status}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByStatusAndStaffId(@PathVariable("staffId") Long id,
                                                                                        @PathVariable("status") String status){
        return ResponseEntity.ok(valuationRequestService.getAcceptedRequestsByConsultingStaffId(id, status));
    }

    @GetMapping("/valuation-request/{staffId}/{status1}/{status2}/{status3}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByTwoStatusAndStaffId(@PathVariable("staffId") Long id,
                                                                                           @PathVariable("status1") String status1,
                                                                                           @PathVariable("status2") String status2,
                                                                                           @PathVariable("status3") String status3){
        return ResponseEntity.ok(valuationRequestService.getRequestsByThreeStatusAndConsultingStaffId(id, status1, status2, status3));
    }

    @GetMapping("/valuation-request/not/{staffId}/{status}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByStaffIdWithNotStatus(@PathVariable("staffId") Long id,
                                                                                            @PathVariable("status") String status){
        return ResponseEntity.ok(valuationRequestService.getValuationRequestByStaffIdNotStatus(id, status));
    }

    @GetMapping("/valuation-request/status/{status}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByStatus(@PathVariable("status") String status){
        return ResponseEntity.ok(valuationRequestService.getRequestsByStatus(status));
    }

    @GetMapping("/valuation-request/status/{status1}/{status2}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByTwoStatus(@PathVariable("status1") String status1, @PathVariable("status2") String status2){
        return ResponseEntity.ok(valuationRequestService.getRequestsByTwoStatus(status1, status2));
    }

    @GetMapping("/valuation-request/status/{status1}/{status2}/{status3}")
    public ResponseEntity<List<ValuationRequest>> getValuationRequestByThreeStatus(@PathVariable("status1") String status1,
                                                                                   @PathVariable("status2") String status2,
                                                                                   @PathVariable("status3") String status3){
        return ResponseEntity.ok(valuationRequestService.getRequestsByThreeStatus(status1, status2, status3));
    }

    @PutMapping("/create-appointment")
    public ValuationRequest createAppointment(@RequestParam("id") Long id, @RequestParam("receiveDate") String date){
        return valuationRequestService.createAppointment(id, date);
    }
}
