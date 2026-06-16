package com.banking.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ExpenseRequest {

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String category;
    private String description;

    // Getters
    public BigDecimal getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }

    // Setters
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
}