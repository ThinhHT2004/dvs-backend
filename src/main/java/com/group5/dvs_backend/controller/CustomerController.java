package com.group5.dvs_backend.controller;


import com.group5.dvs_backend.entity.Customer;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> showAllCustomer(){
        return customerService.getAll();
    }
    @GetMapping("{id}")
    public Customer showCustomerById(@PathVariable Long id){
        return customerService.getCustomerById(id);
    }

    @PutMapping("/update")
    public void updateCustomer(@RequestBody Customer updatedCustomer){
        customerService.updateCustomer(updatedCustomer);

    }
    @GetMapping("/request/{id_customer}")
    public List<ValuationRequest> getRequestById(@PathVariable Long id_customer){
        return customerService.getRequestByCustomerId(id_customer);

    }








}
