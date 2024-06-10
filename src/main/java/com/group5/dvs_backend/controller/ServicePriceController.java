package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.service.ServicePrice;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service-prices")
@AllArgsConstructor
@CrossOrigin
public class ServicePriceController {

    private ServicePrice servicePrice;

    @GetMapping("/price/{id}/{size}")
    public Double getServicePrice(@PathVariable("id") Long id,
                                  @PathVariable("size") Double size){
        return servicePrice.findPriceByIdAndSize(id,size);
    }
}
