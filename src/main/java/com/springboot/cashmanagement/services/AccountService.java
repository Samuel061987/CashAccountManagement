package com.springboot.cashmanagement.services;

import com.springboot.cashmanagement.dto.AccountDTO;
import com.springboot.cashmanagement.dto.AccountRequestDTO;
import com.springboot.cashmanagement.dto.TransactionHistoryDTO;
import com.springboot.cashmanagement.dto.TransactionHistoryResponseDTO;
import com.springboot.cashmanagement.model.Transaction;
import com.springboot.cashmanagement.model.Account;
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

    /**
     * Create new account
     * @param account passing account name and threshold amount
     */

    public void createAccount(AccountRequestDTO account) {
        accountRepository.save(Account.builder()
                .name(account.getName())
                .thresholdAmount(account.getThresholdAmount())
                .build());
    }

    /**
     * TO get the Account list
     * @return Account
     */
    public List<AccountDTO> getAllAccounts() {

        return accountRepository.findAll().stream()
                .map(account ->
                        AccountDTO.builder()
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
    public AccountDTO getAccountById(Long id) {
        Account account =   accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setName(account.getName());
        accountDTO.setThresholdAmount(account.getThresholdAmount());
        accountDTO.setCurrentBalance(account.getBalance());
        return accountDTO;
    }

    /**
     * Process the transaction
     * @param accountId using I'd
     * @param amount amount to process transaction
     */
    @Transactional
    public void processTransaction(Long accountId, double amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
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

    /**
     * To get the transaction history based on accountId
     * @param accountId using I'd
     * @return transaction history response
     */
    public TransactionHistoryResponseDTO getAccountWithTransactionHistory(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));
        List<TransactionHistoryDTO> transactionHistoryDTO = account.getTransactions() != null ?
                account.getTransactions().stream()
                        .map(this::convertToTransactionDTO)
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        TransactionHistoryResponseDTO transactionHistoryResponseDTO = new TransactionHistoryResponseDTO();
        transactionHistoryResponseDTO.setId(account.getId());
        transactionHistoryResponseDTO.setName(account.getName());
        transactionHistoryResponseDTO.setCurrentBalance(account.getBalance());
        transactionHistoryResponseDTO.setThresholdAmount(account.getThresholdAmount());
        transactionHistoryResponseDTO.setTransactionHistoryDTO(transactionHistoryDTO);
        return transactionHistoryResponseDTO;
    }

    /**
     * To get the transaction list in array format
     * @param transaction to get Transaction Id
     * @return transaction history
     */
    private TransactionHistoryDTO convertToTransactionDTO(Transaction transaction) {
        TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();
        transactionHistoryDTO.setTransactionId(transaction.getId());
        transactionHistoryDTO.setType(transaction.getType());
        transactionHistoryDTO.setAmount(transaction.getAmount());
        transactionHistoryDTO.setCreationDate(transaction.getCreationDate());
        return transactionHistoryDTO;
    }

    /**
     * Delete using account Id
     * @param id use account I'd
     */
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}