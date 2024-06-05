package com.group5.dvs_backend.controller;

import com.group5.dvs_backend.entity.Account;
import com.group5.dvs_backend.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> showAllAccount() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public Account showAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
    }

    @PutMapping
    public void updateAccount(@RequestBody Account updatedAccount) {
        accountService.updateAccount(updatedAccount);
    }


}
