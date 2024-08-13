package com.springboot.cashmanagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.springboot.cashmanagement.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testSaveAccount() {
        Account account = new Account();
        account.setName("LOKESH");
        account.setBalance(110.0);
        account.setThresholdAmount(888.0);

        Account savedAccount = accountRepository.save(account);

        assertNotNull(savedAccount.getId());
        assertEquals("LOKESH", savedAccount.getName());
        assertEquals(110.0, savedAccount.getBalance());
        assertEquals(888.0, savedAccount.getThresholdAmount());
    }
}
