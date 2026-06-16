package com.banking.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal dailyLimit;

    @Column(nullable = false)
    private BigDecimal weeklyLimit;

    @Column(nullable = false)
    private BigDecimal monthlyLimit;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    // Getters
    public Long getId() { return id; }
    public BigDecimal getDailyLimit() { return dailyLimit; }
    public BigDecimal getWeeklyLimit() { return weeklyLimit; }
    public BigDecimal getMonthlyLimit() { return monthlyLimit; }
    public Account getAccount() { return account; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setDailyLimit(BigDecimal d) { this.dailyLimit = d; }
    public void setWeeklyLimit(BigDecimal w) { this.weeklyLimit = w; }
    public void setMonthlyLimit(BigDecimal m) { this.monthlyLimit = m; }
    public void setAccount(Account account) { this.account = account; }
}