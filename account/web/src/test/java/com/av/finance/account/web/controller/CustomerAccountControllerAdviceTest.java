package com.av.finance.account.web.controller;

import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.app.service.CustomerNotFoundException;
import com.av.finance.account.common.BusinessException;
import com.av.finance.account.domain.account.CustomerAccountType;
import com.av.finance.account.web.config.CustomerAccountWebTestConfiguration;
import com.av.finance.account.web.dto.AccountInput;
import com.av.finance.account.web.dto.AccountWrongInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerAccountController.class)
@ContextConfiguration(classes = CustomerAccountWebTestConfiguration.class)
@TestPropertySource("classpath:application-unit-test.properties")
class CustomerAccountControllerAdviceTest {

    @Autowired
    private CustomerAccountControllerAdvice customerAccountControllerAdvice;
    @Autowired
    private CustomerAccountService customerAccountService;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AccountInput simpleInput;

    @BeforeEach
    private void init() {
        objectMapper = new ObjectMapper().registerModule(new ParameterNamesModule());
        simpleInput = new AccountInput(
                UUID.randomUUID(),
                CustomerAccountType.CURRENT,
                BigDecimal.valueOf(0L)
        );
    }

    @Test
    void handleBusinessExceptions_success() throws Exception {
        Mockito.when(customerAccountService.openAccount(any(UUID.class), any(CustomerAccountType.class),
                any(BigDecimal.class))).thenThrow(CustomerNotFoundException.class);

        mockMvc.perform(
                        post("/v1/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(simpleInput)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BusinessException));

        Mockito.verify(customerAccountControllerAdvice, times(1))
                .handleBusinessExceptions(any(BusinessException.class));
    }

    @Test
    void handleNoHandlerFoundException_success() throws Exception {
        mockMvc.perform(
                        get("/v1/accounts/absent-url"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NoHandlerFoundException));

        Mockito.verify(customerAccountControllerAdvice, times(1))
                .handleNoHandlerFoundException(any(NoHandlerFoundException.class));
    }

    @Test
    void handleMethodNotAllowedException_success() throws Exception {
        mockMvc.perform(
                        post("/v1/accounts/details")
                                .param("customer_id", UUID.randomUUID().toString()))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof HttpRequestMethodNotSupportedException));

        Mockito.verify(customerAccountControllerAdvice, times(1))
                .handleMethodNotAllowedException(
                        any(HttpRequestMethodNotSupportedException.class),
                        any(HttpServletResponse.class));
    }

    @Test
    void handleUnsupportedMediaTypeException_success() throws Exception {
        mockMvc.perform(
                        post("/v1/accounts")
                                .contentType(MediaType.APPLICATION_PDF)
                                .content(objectMapper.writeValueAsString(simpleInput)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof HttpMediaTypeNotSupportedException));

        Mockito.verify(customerAccountControllerAdvice, times(1))
                .handleUnsupportedMediaTypeException(
                        any(HttpMediaTypeNotSupportedException.class),
                        any(HttpServletResponse.class));
    }

    @Test
    void handleWrongInputExceptions_success() throws Exception {
        final AccountWrongInput accountInput = new AccountWrongInput(
                UUID.randomUUID(),
                CustomerAccountType.CURRENT,
                BigDecimal.valueOf(-1L)
        );

        Mockito.when(customerAccountService.openAccount(any(UUID.class), any(CustomerAccountType.class),
                any(BigDecimal.class))).thenReturn(accountInput.getCustomerId());

        mockMvc.perform(
                        post("/v1/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(accountInput)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

        Mockito.verify(customerAccountControllerAdvice, times(1))
                .handleWrongInputExceptions(any(MethodArgumentNotValidException.class));
    }

    @Test
    void handleUnexpectedExceptions_success() throws Exception {
        Mockito.when(customerAccountService.getCustomerAccountsDetails(any(UUID.class)))
                .thenThrow(RuntimeException.class);

        mockMvc.perform(
                        get("/v1/accounts/details")
                                .param("customer_id", UUID.randomUUID().toString())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof RuntimeException));

        Mockito.verify(customerAccountControllerAdvice, times(1))
                .handleUnexpectedExceptions(any(RuntimeException.class));
    }
}