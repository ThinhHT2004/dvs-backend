package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.ValuationReport;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.service.ValuationReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ValuationReportController {

    private ValuationReportService valuationReportService;

    @PutMapping("/update/{vrId}/{id}")
    public ValuationReport editReport(
            @PathVariable("vrId") Long valuationRequestId,
            @PathVariable("id") Long valuationRequestDetailId,
            @RequestBody ValuationReport valuationReport){
        return valuationReportService.updateValuationReport(valuationRequestId,valuationRequestDetailId,valuationReport);
    }
    @GetMapping("/diamond/{valuationReportId}")
    public ResponseEntity<ValuationReport> findDiamondByValuationReportId(@PathVariable("valuationReportId") String valuationReportId) {
        System.out.println(valuationReportId);
        ValuationReport valuationReport = valuationReportService.findDiamondByValuationReportId(valuationReportId);
        return valuationReport != null ? ResponseEntity.ok(valuationReport) : ResponseEntity.notFound().build();
    }
}
