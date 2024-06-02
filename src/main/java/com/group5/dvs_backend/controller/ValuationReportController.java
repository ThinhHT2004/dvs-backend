package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.ValuationReport;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.service.ValuationReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/reports")
public class ValuationReportController {

    private ValuationReportService valuationReportService;

    @PutMapping("update/{id}")
    public ValuationReport editReport(
            @PathVariable("id") Long valuationRequestDetailId,
            @RequestBody ValuationReport valuationReport){
        return valuationReportService.updateValuationReport(valuationRequestDetailId,valuationReport);
    }
}
