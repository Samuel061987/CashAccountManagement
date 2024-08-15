package com.springboot.cashmanagement.services;

import static org.mockito.Mockito.*;

import com.springboot.cashmanagement.dto.AccountRequestDTO;
import com.springboot.cashmanagement.model.Account;
import com.springboot.cashmanagement.model.Transaction;
import com.springboot.cashmanagement.repository.AccountRepository;
import com.springboot.cashmanagement.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AccountDTOServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessTransaction_Credit() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(100.0);
        account.setThresholdAmount(200.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.processTransaction(1L, 50.0);


        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testProcessTransaction_Debit() {
        Account account = new Account();
        account.setId(1L);
        account.setBalance(300.0);
        account.setThresholdAmount(200.0);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.processTransaction(1L, -150.0);

        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    public void testCreateAccount() {
        AccountRequestDTO account = new AccountRequestDTO();
        account.setName("LOKESH");
        account.setThresholdAmount(888.0);

        accountService.createAccount(account);

        verify(accountRepository, times(1)).save(any(Account.class));
    }
}