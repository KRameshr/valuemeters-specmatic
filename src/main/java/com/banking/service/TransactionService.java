//package com.banking.service;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.banking.dto.TransactionRequest;
//import com.banking.exception.AccountNotFoundException;
//import com.banking.exception.InsufficientBalanceException;
//import com.banking.model.Account;
//import com.banking.model.Transaction;
//import com.banking.repo.AccountRepository;
//import com.banking.repo.TransactionRepository;
//
//@Service
//public class TransactionService {
//
//    @Autowired
//    private TransactionRepository transactionRepository;
//
//    @Autowired
//    private AccountRepository accountRepository;
//
//    // ---------------- VALIDATION ----------------
//    private void validateAmount(BigDecimal amount) {
//        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Invalid amount");
//        }
//    }
//
//    // ---------------- DEPOSIT ----------------
//    public String deposit(Long accountId, TransactionRequest request) {
//
//        validateAmount(request.getAmount());
//
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//
//        account.setBalance(account.getBalance().add(request.getAmount()));
//        accountRepository.save(account);
//
//        Transaction transaction = new Transaction();
//        transaction.setAmount(request.getAmount());
//        transaction.setType(Transaction.TransactionType.CREDIT);
//        transaction.setDescription(request.getDescription());
//        transaction.setDate(LocalDateTime.now());
//        transaction.setAccount(account);
//
//        transactionRepository.save(transaction);
//
//        return "Deposited successfully! New Balance: " + account.getBalance();
//    }
//
//    // ---------------- WITHDRAW ----------------
//    public String withdraw(Long accountId, TransactionRequest request) {
//
//        validateAmount(request.getAmount());
//
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//
//        if (account.getBalance().compareTo(request.getAmount()) < 0) {
//            throw new InsufficientBalanceException("Insufficient balance");
//        }
//
//        account.setBalance(account.getBalance().subtract(request.getAmount()));
//        accountRepository.save(account);
//
//        Transaction transaction = new Transaction();
//        transaction.setAmount(request.getAmount());
//        transaction.setType(Transaction.TransactionType.DEBIT);
//        transaction.setDescription(request.getDescription());
//        transaction.setDate(LocalDateTime.now());
//        transaction.setAccount(account);
//
//        transactionRepository.save(transaction);
//
//        return "Withdrawn successfully! New Balance: " + account.getBalance();
//    }
//
//    // ---------------- TRANSFER ----------------
//    public String transfer(Long fromAccountId, TransactionRequest request) {
//
//        validateAmount(request.getAmount());
//
//        Account fromAccount = accountRepository.findById(fromAccountId)
//                .orElseThrow(() -> new AccountNotFoundException("From account not found"));
//
//        Account toAccount = accountRepository.findByAccountNumber(request.getToAccountNumber())
//                .orElseThrow(() -> new AccountNotFoundException("To account not found"));
//
//        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
//            throw new InsufficientBalanceException("Insufficient balance");
//        }
//
//        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
//        accountRepository.save(fromAccount);
//
//        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
//        accountRepository.save(toAccount);
//
//        Transaction debit = new Transaction();
//        debit.setAmount(request.getAmount());
//        debit.setType(Transaction.TransactionType.DEBIT);
//        debit.setDescription("Transfer to " + request.getToAccountNumber());
//        debit.setDate(LocalDateTime.now());
//        debit.setAccount(fromAccount);
//        transactionRepository.save(debit);
//
//        Transaction credit = new Transaction();
//        credit.setAmount(request.getAmount());
//        credit.setType(Transaction.TransactionType.CREDIT);
//        credit.setDescription("Transfer from " + fromAccount.getAccountNumber());
//        credit.setDate(LocalDateTime.now());
//        credit.setAccount(toAccount);
//        transactionRepository.save(credit);
//
//        return "Transfer successful! New Balance: " + fromAccount.getBalance();
//    }
//
//    // ---------------- HISTORY ----------------
//    public List<Transaction> getTransactionHistory(Long accountId) {
//        return transactionRepository.findByAccountId(accountId);
//    }
//}








package com.banking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.banking.dto.TransactionRequest;
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

    // ---------------- DEPOSIT ----------------
    public String deposit(Long accountId, TransactionRequest request) {

        validateAmount(request);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        saveTransaction(account, request.getAmount(),
                Transaction.TransactionType.CREDIT,
                request.getDescription());

        return "Deposited successfully! New Balance: " + account.getBalance();
    }

    // ---------------- WITHDRAW ----------------
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
                Transaction.TransactionType.DEBIT,
                request.getDescription());

        return "Withdrawn successfully! New Balance: " + account.getBalance();
    }

    // ---------------- TRANSFER ----------------
    public String transfer(Long fromAccountId, TransactionRequest request) {

        validateAmount(request);

        if (request.getToAccountNumber() == null) {
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

    // ---------------- HISTORY ----------------
    public List<Transaction> getTransactionHistory(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    // ---------------- COMMON VALIDATION ----------------
    private void validateAmount(TransactionRequest request) {
        if (request == null || request.getAmount() == null) {
            throw new IllegalArgumentException("Amount is required");
        }

        if (request.getAmount().doubleValue() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
    }

    // ---------------- SAVE TRANSACTION ----------------
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




