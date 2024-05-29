package com.group5.dvs_backend.service;

import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    public void updateAccount(Account updatedAccount) {
        accountRepository.save(updatedAccount);
    }
}