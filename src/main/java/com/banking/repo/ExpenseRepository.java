package com.banking.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.model.Expense;


@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	List<Expense> findByAccountId(Long accountId);
	List<Expense> findByAccountIdAndDateBetween(Long accountId, LocalDateTime start, LocalDateTime end);
}