package com.banking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.dto.TransactionRequest;
import com.banking.dto.TransferRequest;
import com.banking.exception.AccountNotFoundException;
import com.banking.exception.InsufficientBalanceException;
import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.repo.AccountRepository;
import com.banking.repo.TransactionRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository,
                               AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    // DEPOSIT
    public String deposit(Long accountId, TransactionRequest request) {
        validateAmount(request);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);
        saveTransaction(account, request.getAmount(),
                Transaction.TransactionType.CREDIT, null);
        return "Deposited successfully! New Balance: " + account.getBalance();
    }

    // WITHDRAW
    public String withdraw(Long accountId, TransactionRequest request) {
        validateAmount(request);
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);
        saveTransaction(account, request.getAmount(),
                Transaction.TransactionType.DEBIT, null);
        return "Withdrawn successfully! New Balance: " + account.getBalance();
    }

    // TRANSFER
    public String transfer(Long fromAccountId, TransferRequest request) {
        if (request == null || request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (request.getAmount().doubleValue() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (request.getToAccountNumber() == null || request.getToAccountNumber().isBlank()) {
            throw new IllegalArgumentException("Destination account required");
        }
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));
        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
        saveTransaction(fromAccount, request.getAmount(),
                Transaction.TransactionType.DEBIT,
                "Transfer to " + request.getToAccountNumber());
        saveTransaction(toAccount, request.getAmount(),
                Transaction.TransactionType.CREDIT,
                "Transfer from " + fromAccount.getAccountNumber());
        return "Transfer successful! New Balance: " + fromAccount.getBalance();
    }

    // HISTORY
    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    // COMMON VALIDATION
    private void validateAmount(TransactionRequest request) {
        if (request == null || request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }
        if (request.getAmount().doubleValue() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    // SAVE TRANSACTION
    private void saveTransaction(Account account,
                                 java.math.BigDecimal amount,
                                 Transaction.TransactionType type,
                                 String description) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);
        transaction.setDate(LocalDateTime.now());
        transaction.setAccount(account);
        transactionRepository.save(transaction);
    }
}
