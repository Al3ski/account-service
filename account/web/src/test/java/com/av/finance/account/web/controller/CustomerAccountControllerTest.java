package com.av.finance.account.web.controller;

import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.domain.account.CustomerAccountType;
import com.av.finance.account.web.dto.AccountInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CustomerAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerAccountService customerAccountService;

    @Test
    void openCurrentAccountRequest_returnOk() throws Exception {
        final AccountInput accountInput = new AccountInput(
                UUID.randomUUID(),
                CustomerAccountType.CURRENT,
                BigDecimal.ZERO
        );

        Mockito.when(customerAccountService.openAccount(any(UUID.class), any(CustomerAccountType.class),
                any(BigDecimal.class))).thenReturn(accountInput.getCustomerId());

        mockMvc.perform(
                post("/v1/accounts")
                        .content(objectMapper.writeValueAsString(accountInput))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, containsString("/v1/accounts/" + accountInput.getCustomerId())));
    }


    @Configuration
    @ComponentScan(basePackages = {"com.av.finance.account.web"})
    static class CustomerAccountControllerConfiguration {
    }
}