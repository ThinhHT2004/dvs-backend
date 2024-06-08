package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Customer;
import com.group5.dvs_backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAllCustomers();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public void updateCustomer(Customer updatedCustomer) {
        customerRepository.save(updatedCustomer);
    }
    public List<Customer> getRequestByCustomerId(Long id) {
        return customerRepository.findByCustomerId(id);
    }
}
