package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Form;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.service.FormService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@AllArgsConstructor
public class FormController {

    private FormService formService;

    @PostMapping("/create-receipt/{id}")
    public ResponseEntity<Form> createReceipt(@RequestBody List<ValuationRequestDetail> details, @PathVariable("id") Long valuationRequestId) {
        return ResponseEntity.ok(formService.createReceipt(details, valuationRequestId));
    }
}
