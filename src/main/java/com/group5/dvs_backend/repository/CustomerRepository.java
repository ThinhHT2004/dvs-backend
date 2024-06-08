package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllCustomers();
    @Query("SELECT c FROM Customer JOIN c.valuation_request WHERE c.valuation_request.id_customer =?1")
    List<Customer> findByCustomerId(Long id);
}
