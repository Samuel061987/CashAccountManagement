package com.springboot.cashmanagement.controller;
import com.springboot.cashmanagement.dto.*;
import com.springboot.cashmanagement.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/accounts")
@Validated
public class AccountController{
    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create Account", description = "Retrieve a list of all cash accounts",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal server error") })
    @PostMapping("/create")

    public ResponseEntity<ApiResponseDTO> createAccount(@Valid @RequestBody AccountRequestDTO account) {
        accountService.createAccount(account);
        MessageResponseDTO messageResponse = new MessageResponseDTO("Account created successfully");
        ApiResponseDTO response = new ApiResponseDTO(messageResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(summary = "Get all accounts", description = "Retrieve a list of all cash accounts",
            responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error") })
    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }
    @Operation(summary = "Get account details based on accountId ", description = "Retrieve a account details based on account Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error") })

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> getAccountById(@PathVariable Long id) {
        try {
            com.springboot.cashmanagement.model.Account account = accountService.getAccountById(id);
            return ResponseEntity.ok(new ApiResponseDTO(account));
        }catch (RuntimeException e) {
            MessageResponseDTO messageResponse = new MessageResponseDTO("No record found for the given ID");
            ApiResponseDTO response = new ApiResponseDTO(messageResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
    @Operation(summary = "Process the transaction based on account Id", description = "Processing the amount transaction",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error") })

    @PostMapping("/{accountId}/transaction")
    public ResponseEntity<Object> processTransaction(@Valid @PathVariable Long accountId,@Valid @RequestParam double amount) {
       try {
           accountService.processTransaction(accountId, amount);
           MessageResponseDTO messageResponse = new MessageResponseDTO("Transaction processed successfully");
           ApiResponseDTO response = new ApiResponseDTO(messageResponse);
           return ResponseEntity.status(HttpStatus.OK).body(response);
       }
       catch (RuntimeException e) {
           MessageResponseDTO messageResponse = new MessageResponseDTO("No record found for the given ID");
           ApiResponseDTO response = new ApiResponseDTO(messageResponse);
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
       }
    }
    @Operation(summary = "Get the transaction history based on account Id", description = "Retrieve the transaction history based on account Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error") })

    @GetMapping("/{accountId}/transaction-history")
    public  ResponseEntity<Object> getTransactionHistory(@PathVariable Long accountId) {
        try {
            return ResponseEntity.ok(accountService.getAccountWithTransactionHistory(accountId));
        }
        catch (RuntimeException e) {
            MessageResponseDTO messageResponse = new MessageResponseDTO("No record found for the given ID");
            ApiResponseDTO response = new ApiResponseDTO(messageResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

        }
    }
    @Operation(summary = "Delete the account based on account Id", description = "Delete the account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete operation"),
                    @ApiResponse(responseCode = "500", description = "Internal server error") })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        MessageResponseDTO messageResponse = new MessageResponseDTO("Account deleted successfully");
        ApiResponseDTO response = new ApiResponseDTO(messageResponse);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
