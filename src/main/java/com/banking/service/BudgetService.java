package com.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dto.BudgetRequest;
import com.banking.model.Account;
import com.banking.model.Budget;
import com.banking.repo.AccountRepository;
import com.banking.repo.BudgetRepository;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private AccountRepository accountRepository;

    //  Budget
    public String setBudget(Long accountId, BudgetRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        Budget budget = budgetRepository
                .findByAccountId(accountId)
                .orElse(new Budget());

        budget.setDailyLimit(request.getDailyLimit());
        budget.setWeeklyLimit(request.getWeeklyLimit());
        budget.setMonthlyLimit(request.getMonthlyLimit());
        budget.setAccount(account);
        budgetRepository.save(budget);

        return "Budget limits set successfully!";
    }

    // Budget
    public Budget getBudget(Long accountId) {
        return budgetRepository.findByAccountId(accountId)
                .orElseThrow(() -> new RuntimeException("Budget not set!"));
    }
}