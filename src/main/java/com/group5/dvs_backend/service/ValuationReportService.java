package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.ValuationReport;
import com.group5.dvs_backend.entity.ValuationRequestDetail;

public interface ValuationReportService {
    ValuationReport updateValuationReport(Long vrId, Long id, ValuationReport valuationReport);

    ValuationReport findById(Long id);

    ValuationReport save(ValuationReport valuationReport);
}
