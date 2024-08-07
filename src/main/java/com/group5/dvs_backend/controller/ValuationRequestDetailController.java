package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.service.ValuationRequestDetailService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/request-detail")
@AllArgsConstructor
@CrossOrigin
public class ValuationRequestDetailController {

    private ValuationRequestDetailService valuationRequestDetailService;

    @GetMapping("/{status}")
    public List<ValuationRequestDetail> getValuationRequestDetail(@PathVariable("status") String status){
        return valuationRequestDetailService.getValuationRequestDetailByStatus(status);
    }

    @PutMapping("/update")
    public ValuationRequestDetail updateValuationRequestDetail(@RequestBody ValuationRequestDetail valuationRequestDetail){
        return valuationRequestDetailService.updateRequest(valuationRequestDetail);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ValuationRequestDetail> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(valuationRequestDetailService.getById(id));
    }

    @PutMapping("/deny/{id}")
    public ValuationRequestDetail denySampe(@PathVariable("id") Long id){
        return valuationRequestDetailService.deny(id);
    }

}
