package com.av.finance.account.app.service;

import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.common.RequestProperties;
import com.av.finance.account.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
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
    private RequestProperties requestProperties;
    @MockBean
    private RestClient restClient;

    @Test
    void createTransaction_success() {
        Mockito.when(restClient.post(any(String.class), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        Mockito.when(requestProperties.getTransactionUrl()).thenReturn("/test");

        transactionService.createTransaction(UUID.randomUUID(), TransactionType.INITIAL,
                BigDecimal.ZERO, "");

        Mockito.verify(restClient, times(1))
                .post(any(String.class), any(HttpEntity.class), any(ParameterizedTypeReference.class));
    }
}