package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.ValuationRequestDetail;

import java.util.List;

public interface ValuationRequestDetailService {
    List<ValuationRequestDetail> getValuationRequestDetailByStatus(String status);

    ValuationRequestDetail updateRequest(ValuationRequestDetail valuationRequestDetail);
    ValuationRequestDetail getById(Long id);
}
