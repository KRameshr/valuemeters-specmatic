//package com.banking.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.banking.dto.TransactionRequest;
//import com.banking.model.Transaction;
//import com.banking.service.TransactionService;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/transaction")
//@Tag(name = "Transaction", description = "Transaction management APIs")
//public class TransactionController {
//
//	@Autowired
//	private TransactionService transactionService;
//
//	@Operation(summary = "Deposit money")
//	@PostMapping("/deposit/{accountId}")
//	public ResponseEntity<String> deposit(@PathVariable Long accountId,
//			@Valid @RequestBody TransactionRequest request) {
//
//		String response = transactionService.deposit(accountId, request);
//
//		return ResponseEntity.ok(response);
//	}
//
//	@Operation(summary = "Withdraw money")
//	@PostMapping("/withdraw/{accountId}")
//	public ResponseEntity<String> withdraw(@PathVariable Long accountId,
//			@Valid @RequestBody TransactionRequest request) {
//
//		String response = transactionService.withdraw(accountId, request);
//
//		return ResponseEntity.ok(response);
//	}
//
//
//	@Operation(summary = "Transfer money to another account")
//	@PostMapping("/transfer/{fromAccountId}")
//	public ResponseEntity<String> transfer(@PathVariable Long fromAccountId,
//			@Valid @RequestBody TransactionRequest request) {
//
//		String response = transactionService.transfer(fromAccountId, request);
//
//		return ResponseEntity.ok(response);
//	}
//
//	
//	@Operation(summary = "Get transaction history")
//	@GetMapping("/history/{accountId}")
//	public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accountId) {
//
//		List<Transaction> transactions = transactionService.getTransactionHistory(accountId);
//
//		return ResponseEntity.ok(transactions);
//	}
//}











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

    // ---------------- DEPOSIT ----------------
    @Operation(summary = "Deposit money")
    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<Object> deposit(
            @PathVariable String accountId,
            @Valid @RequestBody TransactionRequest request) {

        Long id = parseAccountId(accountId);
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        return ResponseEntity.ok(transactionService.deposit(id, request));
    }

    // ---------------- WITHDRAW ----------------
    @Operation(summary = "Withdraw money")
    @PostMapping("/withdraw/{accountId}")
    public ResponseEntity<Object> withdraw(
            @PathVariable String accountId,
            @Valid @RequestBody TransactionRequest request) {

        Long id = parseAccountId(accountId);
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        return ResponseEntity.ok(transactionService.withdraw(id, request));
    }

    // ---------------- TRANSFER ----------------
    @Operation(summary = "Transfer money")
    @PostMapping("/transfer/{fromAccountId}")
    public ResponseEntity<Object> transfer(
            @PathVariable String fromAccountId,
            @Valid @RequestBody TransactionRequest request) {

        Long id = parseAccountId(fromAccountId);
        if (id == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Invalid accountId"));
        }

        return ResponseEntity.ok(transactionService.transfer(id, request));
    }

    // ---------------- HISTORY ----------------
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

    // ---------------- SAFE PARSER ----------------
    private Long parseAccountId(String accountId) {
        try {
            return Long.parseLong(accountId);
        } catch (Exception e) {
            return null;
        }
    }
}