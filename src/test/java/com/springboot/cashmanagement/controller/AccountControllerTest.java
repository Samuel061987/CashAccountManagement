package com.springboot.cashmanagement.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cashmanagement.dto.AccountRequest;
import com.springboot.cashmanagement.dto.Account;
import com.springboot.cashmanagement.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testGetAllAccounts() throws Exception {


        List<Account> accounts = Arrays.asList(Account.builder().name("User1").id(1L).currentBalance(100.0).thresholdAmount(200.0).build()
                , Account.builder().name("User2").id(2L).currentBalance(150.0).thresholdAmount(200.0).build());

       when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("User1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("User2"));
    }

    @Test
    public void testCreateAccount() throws Exception {
        AccountRequest account = new AccountRequest();
        account.setName("LOKESH");
        account.setThresholdAmount(888.0);

        mockMvc.perform(post("/api/accounts/create")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isCreated());

       verify(accountService, times(1))
               .createAccount(any(AccountRequest.class));
    }
}
