package com.banking.service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.dto.ExpenseRequest;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InsufficientBalanceException;
import com.banking.model.Account;
import com.banking.model.Expense;
import com.banking.repo.AccountRepository;
import com.banking.repo.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AccountRepository accountRepository;

    public String addExpense(Long accountId, ExpenseRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found!"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance!");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(Expense.Category.valueOf(
            request.getCategory().toUpperCase()));
        expense.setDescription(request.getDescription());
        expense.setDate(LocalDateTime.now());
        expense.setAccount(account);
        expenseRepository.save(expense);

        return "Expense added! Remaining balance: " + account.getBalance();
    }

    public List<Expense> getExpenses(Long accountId) {
        return expenseRepository.findByAccountId(accountId);
    }

    public Map<String, Object> getBudgetSummary(Long accountId) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        List<Expense> daily = expenseRepository
                .findByAccountIdAndDateBetween(accountId, startOfDay, now);

        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY)
                .toLocalDate().atStartOfDay();
        List<Expense> weekly = expenseRepository
                .findByAccountIdAndDateBetween(accountId, startOfWeek, now);

        LocalDateTime startOfMonth = now
                .with(TemporalAdjusters.firstDayOfMonth())
                .toLocalDate().atStartOfDay();
        List<Expense> monthly = expenseRepository
                .findByAccountIdAndDateBetween(accountId, startOfMonth, now);

        BigDecimal dailyTotal = daily.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal weeklyTotal = weekly.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal monthlyTotal = monthly.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> summary = new HashMap<>();
        summary.put("dailySpent", dailyTotal);
        summary.put("weeklySpent", weeklyTotal);
        summary.put("monthlySpent", monthlyTotal);
        return summary;
    }
}
