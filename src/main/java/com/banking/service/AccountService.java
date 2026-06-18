package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.model.Account;
import com.banking.repo.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //  Account by User ID
    public Account getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));
    }

    //  Account by Account Number
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found!"));
    }
}