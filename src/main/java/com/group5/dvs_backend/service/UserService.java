package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.entity.Customer;
import com.group5.dvs_backend.repository.AccountRepository;
import com.group5.dvs_backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCruptPasswordEncoder();

    public Account registerUser(String username,String password , String confirmPassword) {
        if (accountRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exist!");
        }

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match!");
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole("Customer");

        Account savedAccount = accountRepository.save(account);

        Customer customer = new Customer();
        customer.setId(savedAccount.getId());
        customerRepository.save(customer);

        return savedAccount;
    }
    public Account loginUser(String username,String password){
        Account account = accountRepository.findByUsername(username).orElse(null);

        if(!passwordEncoder.matches(password,account.getPassword())){
            throw new RuntimeException("Wrong password!");
        }
        return account;
    }



    }




