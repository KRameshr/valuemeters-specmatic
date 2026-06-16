

package com.banking.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public enum Category {
        FOOD, TRANSPORT, HEALTH, EDUCATION, OTHERS
    }

    // Getters
    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public Category getCategory() { return category; }
    public String getDescription() { return description; }
    public LocalDateTime getDate() { return date; }
    public Account getAccount() { return account; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setCategory(Category category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public void setAccount(Account account) { this.account = account; }
}