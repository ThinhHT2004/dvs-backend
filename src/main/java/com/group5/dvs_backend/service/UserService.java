package com.group5.dvs_backend.service;

import com.group5.dvs_backend.config.JwtService;
import com.group5.dvs_backend.entity.*;
import com.group5.dvs_backend.enums.Roles;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.payload.*;
import com.group5.dvs_backend.repository.AccountRepository;
import com.group5.dvs_backend.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private EmailService emailService;

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

    public AuthResponse authenticate(AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            Account account = accountRepository.findByUsername(request.getUsername()).orElseThrow();
            if (!account.isActive()) {
                return AuthResponse
                        .builder()
                        .role("EMPTY")
                        .mess("Account is not verified")
                        .build();
            }
            var token = jwtService.generateToken(account);
            return AuthResponse
                    .builder()
                    .role(account.getRole())
                    .id(account.getId())
                    .mess("Login Successfully")
                    .token(token)
                    .build();
        }catch (BadCredentialsException bad){
            return AuthResponse
                    .builder()
                    .role("EMPTY")
                    .mess("Password is incorrect")
                    .build();
        } catch (InternalAuthenticationServiceException e) {
            return AuthResponse
                    .builder()
                    .role("EMPTY")
                    .mess("Username does not exist")
                    .build();
        }
    }

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse registerResponse = new RegisterResponse();
        if (accountRepository.findByUsername(request.getUsername()).isPresent()) {
            registerResponse.setMess("User already exist!");
            registerResponse.setCode(0L);
            return registerResponse;
        }

        if (request.getDob().after(new Date())) {
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
        Customer savedCustomer = customerRepository.save(customer);

        String token = generateConfirmationToken(savedCustomer);

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(savedCustomer.getEmail());
        emailDetail.setSubject("Diascur Account Verification");

        Context context = new Context();
        context.setVariable("firstName", savedCustomer.getFirst_name());
        context.setVariable("lastName", savedCustomer.getLast_name());
        context.setVariable("token", token);

        emailService
                .sendMailTemplate(emailDetail, "SendTokenTemplate.html", context);

        return registerResponse;
    }

    private String generateConfirmationToken(Customer customer) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = ConfirmationToken
                .builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .customer(customer)
                .build();

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public ConfirmationTokenResponse confirmToken(String token, String type) {
        ConfirmationTokenResponse response;
        Optional<ConfirmationToken> optionConfirmationToken = confirmationTokenService
                .getToken(token);

        System.out.println(type);

        if (!optionConfirmationToken.isPresent()) {
            response = ConfirmationTokenResponse
                    .builder()
                    .text("Token is incorrect")
                    .code(0)
                    .build();
            return response;
        }

        ConfirmationToken confirmationToken = optionConfirmationToken.get();

        if (confirmationToken.getConfirmedAt() != null) {
            response = ConfirmationTokenResponse
                    .builder()
                    .text("Email already confirmed")
                    .code(0)
                    .build();
            return response;
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            response = ConfirmationTokenResponse
                    .builder()
                    .text("Token has been expired")
                    .code(0)
                    .build();
            return response;
        }

        confirmationTokenService.setConfirmedAt(token);

        if (type.equalsIgnoreCase("register")) {
            Account account = accountRepository
                    .findById(confirmationToken.getCustomer().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No Account Found"));

            account.setActive(true);

            accountRepository.save(account);
            response = ConfirmationTokenResponse
                    .builder()
                    .text("Email has been verified, Create Account Successfully")
                    .type("Register")
                    .code(1)
                    .build();
            return response;
        } else {
            response = ConfirmationTokenResponse
                    .builder()
                    .text("Ready to reset Password")
                    .code(1)
                    .type("Reset")
                    .build();
            return response;
        }
    }

    public Long sendVerification(String username) {
        Optional<Account> optional = accountRepository.findByUsername(username);

        if (optional.isEmpty()){
            return 0L;
        }

        Account account = optional.get();
        Customer customer = customerRepository
                .findByCustomerId(account.getId());
        String token = generateConfirmationToken(customer);

        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setRecipient(customer.getEmail());
        emailDetail.setSubject("Diascur Account Verification");

        Context context = new Context();
        context.setVariable("fistName", customer.getFirst_name());
        context.setVariable("lastName", customer.getLast_name());
        context.setVariable("token", token);

        emailService
                .sendMailTemplate(emailDetail, "ResetEmailTemplate.html", context);

        return 1L;
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
        ChangePasswordResponse response = new ChangePasswordResponse();

        Optional<Account> optionalAccount = accountRepository.findByUsername(request.getUsername());

        if (optionalAccount.isEmpty()){
            response = ChangePasswordResponse
                    .builder()
                    .mess("Username Not Found")
                    .code(0)
                    .build();
            return response;
        }

        Account account = optionalAccount.get();

        account.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(account);

        response = ChangePasswordResponse
                .builder()
                .mess("Change Password Successfully")
                .code(1)
                .build();

        return response;
    }
}




