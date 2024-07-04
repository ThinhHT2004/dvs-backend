package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.exception.ResourceNotFoundException;
import com.group5.dvs_backend.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void updateAccount(Account updatedAccount) {
        accountRepository.save(updatedAccount);
    }

    public String disable(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Account Found"));
        account.setActive(false);
        accountRepository.save(account);

        return "Disable Account Successfully";
    }

    public String enable(Long id){
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Account Found"));
        account.setActive(true);
        accountRepository.save(account);

        return "Enable Account Successfully";
    }
}