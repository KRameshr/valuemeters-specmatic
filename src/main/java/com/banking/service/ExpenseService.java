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

    //  Add Expense
    public String addExpense(Long accountId, ExpenseRequest request) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found!"));

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance!");
        }

        // Deduct balance
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        // Save expense
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(Expense.Category.valueOf(
            request.getCategory().toUpperCase()));
        expense.setDescription(request.getDescription());
        expense.setDate(LocalDateTime.now());
        expense.setAccount(account);
        expenseRepository.save(expense);

        return "Expense added! Remaining balance: ₹" + account.getBalance();
    }

    //   All Expenses
    public List<Expense> getExpenses(Long accountId) {
        return expenseRepository.findByAccountId(accountId);
    }

    //  Budget Summary
    public Map<String, Object> getBudgetSummary(Long accountId) {
        LocalDateTime now = LocalDateTime.now();

        // Daily
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        List<Expense> daily = expenseRepository
                .findByAccountIdAndDateBetween(accountId, startOfDay, now);

        // Weekly
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY)
                .toLocalDate().atStartOfDay();
        List<Expense> weekly = expenseRepository
                .findByAccountIdAndDateBetween(accountId, startOfWeek, now);

        // Monthly
        LocalDateTime startOfMonth = now
                .with(TemporalAdjusters.firstDayOfMonth())
                .toLocalDate().atStartOfDay();
        List<Expense> monthly = expenseRepository
                .findByAccountIdAndDateBetween(accountId, startOfMonth, now);

        // Totals
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