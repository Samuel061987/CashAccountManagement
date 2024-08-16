package com.springboot.cashmanagement.repository;
import com.springboot.cashmanagement.model.Account;
import com.springboot.cashmanagement.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Transaction t WHERE t.account.id = :accountId")
    void deleteByAccountId(@Param("accountId") Long accountId);
    List<Transaction> findByAccount(Account account);
}