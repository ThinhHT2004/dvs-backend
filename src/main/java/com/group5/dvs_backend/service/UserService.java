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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
                        .mess("Username or Password is Incorrect")
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
        } catch (Exception e) {
            return AuthResponse
                    .builder()
                    .role("EMPTY")
                    .mess("Username or Password is Incorrect")
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

        emailService
                .sendMailTemplate(emailDetail, buildRegisterEmail(savedCustomer.getLast_name() + savedCustomer.getFirst_name(), token));

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

    private String buildRegisterEmail(String name, String token) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. This is your verification code: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    private String buildResetEmail(String name, String token) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your account</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Ready to reset password. This is your verification code: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">" + token + "</p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
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

        emailService
                .sendMailTemplate(emailDetail, buildResetEmail(customer.getLast_name() + customer.getFirst_name(), token));

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




