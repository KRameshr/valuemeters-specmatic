package com.banking.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banking.model.Budget;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
	Optional<Budget> findByAccountId(Long accountId);
}