package com.av.finance.account.app.service;

import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@Import(ApplicationServiceTestConfiguration.class)
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @MockBean
    private RestClient restClient;

    @Test
    void createTransaction_success() {
        Mockito.when(restClient.postRetryable(any(String.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        transactionService.createTransaction(UUID.randomUUID(), TransactionType.INITIAL,
                BigDecimal.ZERO, "");

        Mockito.verify(restClient, times(1))
                .postRetryable(any(String.class), any(HttpEntity.class), any(Class.class));
    }
}