package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Form;
import com.group5.dvs_backend.entity.ValuationRequestDetail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface FormService {
    Form createForm(Form form);

    Form createReceipt(List<ValuationRequestDetail> valuationRequestDetails, Long valuationRequestId, Long total);

    List<Form> getWaitingForms();

    Form approveForm(Long formId);

    Form denyForm(Long formId);

    List<Form> getReceipts();
    List<Form> getReceiptsByTimeRange(LocalDate from, LocalDate to);
}
