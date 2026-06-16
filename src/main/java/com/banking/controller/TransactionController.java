package com.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.banking.dto.TransactionRequest;
import com.banking.model.Transaction;
import com.banking.service.TransactionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;

@RestController
@RequestMapping("/transaction")
@Tag(name = "Transaction", description = "Transaction management APIs")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Operation(summary = "Deposit money")
	@PostMapping("/deposit/{accountId}")
	public ResponseEntity<String> deposit(@PathVariable Long accountId,
			@Valid @RequestBody TransactionRequest request) {

		String response = transactionService.deposit(accountId, request);

		return ResponseEntity.ok(response);
	}

	@Operation(summary = "Withdraw money")
	@PostMapping("/withdraw/{accountId}")
	public ResponseEntity<String> withdraw(@PathVariable Long accountId,
			@Valid @RequestBody TransactionRequest request) {

		String response = transactionService.withdraw(accountId, request);

		return ResponseEntity.ok(response);
	}


	@Operation(summary = "Transfer money to another account")
	@PostMapping("/transfer/{fromAccountId}")
	public ResponseEntity<String> transfer(@PathVariable Long fromAccountId,
			@Valid @RequestBody TransactionRequest request) {

		String response = transactionService.transfer(fromAccountId, request);

		return ResponseEntity.ok(response);
	}

	
	@Operation(summary = "Get transaction history")
	@GetMapping("/history/{accountId}")
	public ResponseEntity<List<Transaction>> getHistory(@PathVariable Long accountId) {

		List<Transaction> transactions = transactionService.getTransactionHistory(accountId);

		return ResponseEntity.ok(transactions);
	}
}