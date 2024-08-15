package com.springboot.cashmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.springboot.cashmanagement.model.Account;
import com.springboot.cashmanagement.model.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveTransaction() {
        Account account = new Account();
        account.setName("LOKESH");
        account.setBalance(110.0);
        account.setThresholdAmount(888.0);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(50.0);
        transaction.setType("CREDIT");
        transaction.setCreationDate(LocalDateTime.now());
        transaction.setAccount(account);

        Transaction savedTransaction = transactionRepository.save(transaction);

        assertNotNull(savedTransaction.getId());
        assertEquals(50.0, savedTransaction.getAmount());
        assertEquals("CREDIT", savedTransaction.getType());
        assertEquals(account.getId(), savedTransaction.getAccount().getId());
    }
}