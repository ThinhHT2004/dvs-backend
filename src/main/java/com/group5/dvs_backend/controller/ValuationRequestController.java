package com.group5.dvs_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.service.ValuationRequestService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValuationRequestController {

    private final ValuationRequestService valuationRequestService;

    public ValuationRequestController(ValuationRequestService valuationRequestService) {
        this.valuationRequestService = valuationRequestService;
    }

    @RequestMapping("???", method=RequestMethod.POST)
    public void HandlerValuationRequestInfor(@ModelAttribute("newValuationRequest") ValuationRequest valuationRequest){
        this.valuationRequestService.saveValuationRequestInfor(valuationRequest);

    }
}
