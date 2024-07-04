package com.group5.dvs_backend.service;

import com.group5.dvs_backend.config.JwtService;
import com.group5.dvs_backend.entity.*;
import com.group5.dvs_backend.enums.Roles;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.AccountRepository;
import com.group5.dvs_backend.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;


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

    public AuthResponse loginUser(Auth auth) {
        AuthResponse authResponse = new AuthResponse();
        Optional<Account> account = accountRepository.findByUsername(auth.getUsername());
        Account respAccount = null;
        if (account.isPresent()) {
            respAccount = account.get();
            if (!passwordEncoder.matches(auth.getPassword(), respAccount.getPassword())) {
                authResponse.setMess("Wrong password!");
                authResponse.setId(-1L);
                authResponse.setRole("");
            } else {
                authResponse.setMess("Login Successfully");
                authResponse.setId(respAccount.getId());
                authResponse.setRole(respAccount.getRole());
            }
        } else {
            authResponse.setMess("Wrong Username!");
            authResponse.setId(-1L);
            authResponse.setRole("");
        }

        return authResponse;
    }

    public AuthResponse authenticate(AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Account account = accountRepository.findByUsername(request.getUsername()).orElseThrow();
        var token = jwtService.generateToken(account);
        return AuthResponse
                .builder()
                .role(account.getRole())
                .id(account.getId())
                .mess("Login Successfully")
                .token(token)
                .build();
    }

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (accountRepository.findByUsername(request.getUsername()).isPresent()) {
            registerResponse.setMess("User already exist!");
            registerResponse.setCode(0L);
            return registerResponse;
        }

        if (request.getDob().after(new Date())){
            registerResponse.setMess("Date of Birth must before Today");
            registerResponse.setCode(0L);
            return registerResponse;
        }


        Account account = Account
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Roles.CUSTOMER.name())
                .build();
        Account savedAccount = accountRepository.save(account);

        Customer customer = Customer
                .builder()
                .first_name(request.getFirstName())
                .last_name(request.getLastName())
                .email(request.getEmail())
                .dob(request.getDob())
                .phoneNumber(request.getPhoneNumber())
                .address("")
                .build();
        customer.setId(savedAccount.getId());
        customerRepository.save(customer);

        return registerResponse;
    }
}




