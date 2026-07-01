package com.banking.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.dto.TransactionRequest;
import com.banking.dto.TransferRequest;
import com.banking.model.Transaction;
import com.banking.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Transaction management APIs")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //  DEPOSIT
    @Operation(summary = "Deposit money")
    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Object> deposit(
            @PathVariable String accountId,
            @Valid @RequestBody TransactionRequest request) {

          System.out.println("WITHDRAW ACCOUNT ID = " + accountId);
          System.out.println("WITHDRAW AMOUNT = " + request.getAmount());

        Long id = parseAccountId(accountId);
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        String response = transactionService.deposit(id, request);
        return ResponseEntity.ok(Map.of("message", response));
    }

    // WITHDRAW
    @Operation(summary = "Withdraw money")
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Object> withdraw(
            @PathVariable String accountId,
            @Valid @RequestBody TransactionRequest request) {

        System.out.println("WITHDRAW ACCOUNT ID = " + accountId);

        if (request != null) {
            System.out.println("WITHDRAW AMOUNT = " + request.getAmount());
        }

        Long id = parseAccountId(accountId);

        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        String response = transactionService.withdraw(id, request);

        return ResponseEntity.ok(
                Map.of("message", response)
        );
    }
    // TRANSFER
    @Operation(summary = "Transfer money")
    @PostMapping("/transfer/{fromAccountId}")
    public ResponseEntity<Object> transfer(
            @PathVariable String fromAccountId,
            @Valid @RequestBody TransferRequest request) {

        Long id = parseAccountId(fromAccountId);
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        String response = transactionService.transfer(id, request);
        return ResponseEntity.ok(Map.of("message", response));
    }

    // HISTORY
    @Operation(summary = "Get transaction history")
    @GetMapping("/history/{accountId}")
    public ResponseEntity<Object> getHistory(@PathVariable String accountId) {

        Long id = parseAccountId(accountId);
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        List<Transaction> transactions = transactionService.getTransactionHistory(id);
        return ResponseEntity.ok(transactions);
    }

    // SAFE PARSER
    private Long parseAccountId(String accountId) {
        try {
            return Long.parseLong(accountId);
        } catch (Exception e) {
            return null;
        }
    }
}