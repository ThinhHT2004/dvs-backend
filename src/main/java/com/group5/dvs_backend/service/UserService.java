package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.entity.Auth;
import com.group5.dvs_backend.entity.AuthResponse;
import com.group5.dvs_backend.entity.Customer;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.AccountRepository;
import com.group5.dvs_backend.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public AuthResponse loginUser(Auth auth){
        AuthResponse authResponse = new AuthResponse();
        Optional<Account> account = accountRepository.findByUsername(auth.getUsername());
        Account respAccount = null;
        if (account.isPresent()){
            respAccount = account.get();
            if(!passwordEncoder.matches(auth.getPassword(),respAccount.getPassword())){
                authResponse.setMess("Wrong password!");
                authResponse.setId(-1L);
                authResponse.setRole("");
            }else {
                authResponse.setMess("Login Successfully");
                authResponse.setId(respAccount.getId());
                authResponse.setRole(respAccount.getRole());
            }
        }else{
            authResponse.setMess("Wrong Username!");
            authResponse.setId(-1L);
            authResponse.setRole("");
        }

        return authResponse;
    }



    }




