package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.entity.Auth;
import com.group5.dvs_backend.entity.Customer;
import com.group5.dvs_backend.repository.AccountRepository;
import com.group5.dvs_backend.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor

public class UserService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;



    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    



    public Account registerUser(Auth auth) {
        String username = auth.getUsername();
        String password = auth.getPassword();
        String confirmPassword = auth.getConfirmPassword();
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
    public Account loginUser(Auth auth){
        Account account = accountRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        if(!passwordEncoder.matches(auth.getPassword(),account.getPassword())){
            throw new RuntimeException("Wrong password!");
        }
        return account;
    }



    }




