package com.group5.dvs_backend.repository;

import com.group5.dvs_backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c")
    List<Customer> findAllCustomers();
    @Query("SELECT c FROM Customer c JOIN c.valuation_request vr WHERE vr.customer.id =?1")
    List<Customer> findByCustomerId(Long id_customer);

    Optional<Customer> findByEmail(String email);
}
