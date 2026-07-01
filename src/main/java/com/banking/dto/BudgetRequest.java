package com.banking.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class BudgetRequest {

    @NotNull(message = "Daily limit is required")
    @Positive(message = "Daily limit must be greater than zero")
    private BigDecimal dailyLimit;

    @NotNull(message = "Weekly limit is required")
    @Positive(message = "Weekly limit must be greater than zero")
    private BigDecimal weeklyLimit;

    @NotNull(message = "Monthly limit is required")
    @Positive(message = "Monthly limit must be greater than zero")
    private BigDecimal monthlyLimit;

    public BigDecimal getDailyLimit() { return dailyLimit; }
    public BigDecimal getWeeklyLimit() { return weeklyLimit; }
    public BigDecimal getMonthlyLimit() { return monthlyLimit; }

    public void setDailyLimit(BigDecimal d) { this.dailyLimit = d; }
    public void setWeeklyLimit(BigDecimal w) { this.weeklyLimit = w; }
    public void setMonthlyLimit(BigDecimal m) { this.monthlyLimit = m; }
}
