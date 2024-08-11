package com.springboot.cashmanagement.services;

import com.springboot.cashmanagement.dto.Account;
import com.springboot.cashmanagement.dto.AccountRequest;
import com.springboot.cashmanagement.dto.TransactionHistory;
import com.springboot.cashmanagement.dto.TransactionHistoryResponse;
import com.springboot.cashmanagement.model.Transaction;
import com.springboot.cashmanagement.repository.AccountRepository;
import com.springboot.cashmanagement.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public void createAccount(AccountRequest account) {
        accountRepository.save(com.springboot.cashmanagement.model.Account.builder()
                .name(account.getName())
                .thresholdAmount(account.getThresholdAmount())
                .build());
    }

    public List<Account> getAllAccounts() {

        return accountRepository.findAll().stream()
                .map(account ->
                    Account.builder()
                            .id(account.getId())
                            .name(account.getName())
                            .thresholdAmount(account.getThresholdAmount())
                            .build()
                    ).collect(Collectors.toList());
    }

    /**
     * TO get the Account using I'd
     * @param id AccountID
     * @return Account
     */
    public com.springboot.cashmanagement.model.Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
    }
    @Transactional
    public void processTransaction(Long accountId, double amount) {
        com.springboot.cashmanagement.model.Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        double newBalance = account.getBalance() + amount;
        String transactionType = null;
// The account needs to be credited to when the new balance is below the threshold
        if (newBalance <= account.getThresholdAmount()) {
           // Credit
            account.setBalance(newBalance);
            transactionType = "CREDIT";
        }
        //The account needs to be debited from when the new balance is above the threshold
        else if(newBalance > account.getThresholdAmount()) {
            // Debit
            account.setBalance(account.getBalance() - amount);
            transactionType = "DEBIT";
        }


        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(transactionType);
        transaction.setAccount(account);
        transactionRepository.save(transaction);

       accountRepository.save(account);
    }

    public TransactionHistoryResponse getAccountWithTransactionHistory(Long accountId) {
        com.springboot.cashmanagement.model.Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        List<TransactionHistory> transactionHistory = account.getTransactions() != null ?
                account.getTransactions().stream()
                        .map(this::convertToTransactionDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        TransactionHistoryResponse transactionHistoryResponse = new TransactionHistoryResponse();
        transactionHistoryResponse.setId(account.getId());
        transactionHistoryResponse.setName(account.getName());
        transactionHistoryResponse.setCurrentBalance(account.getBalance());
        transactionHistoryResponse.setThresholdAmount(account.getThresholdAmount());
        transactionHistoryResponse.setTransactionHistory(transactionHistory);
        return transactionHistoryResponse;
    }

    private TransactionHistory convertToTransactionDTO(Transaction transaction) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionId(transaction.getId());
        transactionHistory.setType(transaction.getType());
        transactionHistory.setAmount(transaction.getAmount());
        transactionHistory.setCreationDate(transaction.getCreationDate());
        return transactionHistory;
    }
}
