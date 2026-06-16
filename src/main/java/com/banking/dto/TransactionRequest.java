package com.banking.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class TransactionRequest {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String description;
    private String toAccountNumber;

    // Getters
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getToAccountNumber() { return toAccountNumber; }

    // Setters
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setDescription(String description) { this.description = description; }
    public void setToAccountNumber(String toAccountNumber) { this.toAccountNumber = toAccountNumber; }
}