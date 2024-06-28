package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Form;
import com.group5.dvs_backend.entity.ValuationRequestDetail;
import com.group5.dvs_backend.service.FormService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@AllArgsConstructor
@CrossOrigin
public class FormController {

    private FormService formService;

    @PostMapping("/create-receipt/{id}")
    public ResponseEntity<Form> createReceipt(@RequestBody List<ValuationRequestDetail> details, @PathVariable("id") Long valuationRequestId) {
        return ResponseEntity.ok(formService.createReceipt(details, valuationRequestId));
    }

    @GetMapping("/waiting")
    public ResponseEntity<List<Form>> getWaitingForms(){
        return ResponseEntity.ok(formService.getWaitingForms());
    }

    @PostMapping("/create-form")
    public ResponseEntity<Form> createForm(@RequestBody Form form){
        return ResponseEntity.ok(formService.createForm(form));
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Form> approveForm(@PathVariable("id") Long formId){
        return ResponseEntity.ok(formService.approveForm(formId));
    }

    @PostMapping("/deny/{id}")
    public ResponseEntity<Form> denyForm(@PathVariable("id") Long formId){
        return ResponseEntity.ok(formService.denyForm(formId));
    }
}
