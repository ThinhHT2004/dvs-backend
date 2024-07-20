package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Customer;
import com.group5.dvs_backend.entity.ValuationRequest;
import com.group5.dvs_backend.payload.UpdateResponse;
import com.group5.dvs_backend.repository.CustomerRepository;
import com.group5.dvs_backend.repository.ValuationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ValuationRequestRepository valuationRequestRepository;

    public List<Customer> getAll() {
        return customerRepository.findAllCustomers();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public UpdateResponse updateCustomer(Customer updatedCustomer) {

        if (updatedCustomer.getDob().after(new Date())) {
            return UpdateResponse
                    .builder()
                    .message("The date of birth must be before today")
                    .code(0L)
                    .build();
        }
        customerRepository.save(updatedCustomer);

        return UpdateResponse
                .builder()
                .message("Update Successfully")
                .code(1L)
                .build();
    }
    public List<ValuationRequest> getRequestByCustomerId(Long id_customer) {
        return valuationRequestRepository.findByCustomerId(id_customer);
    }
}
