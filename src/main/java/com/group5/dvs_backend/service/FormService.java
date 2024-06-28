package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Form;
import com.group5.dvs_backend.entity.ValuationRequestDetail;

import java.util.List;

public interface FormService {
    Form createForm(Form form);

    Form createReceipt(List<ValuationRequestDetail> valuationRequestDetails, Long valuationRequestId);

    List<Form> getWaitingForms();

    Form approveForm(Long formId);

    Form denyForm(Long formId);
}
