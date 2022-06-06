package com.av.finance.account.infrastructure.rest;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.external.TransactionExternalService;
import com.av.finance.account.common.RequestProperties;
import com.av.finance.account.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
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
class TransactionExternalServiceTest {

    @Autowired
    private TransactionExternalService transactionService;

    @MockBean
    private RequestProperties requestProperties;
    @MockBean
    private RestClient restClient;

    @Test
    void createTransaction_success() {
        Mockito.when(restClient.post(any(String.class), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
        Mockito.when(requestProperties.getTransactionUrl()).thenReturn("/test");

        transactionService.createTransaction(TxDetails.builder()
                .accountId(UUID.randomUUID())
                .txType(TransactionType.INITIAL)
                .amount(BigDecimal.ZERO)
                .details("")
                .build());

        Mockito.verify(restClient, times(1))
                .post(any(String.class), any(HttpEntity.class), any(ParameterizedTypeReference.class));
    }

    @TestConfiguration
    @ComponentScan(basePackages = "com.av.finance.account.infrastructure.rest")
    static class ApplicationServiceTestConfiguration {
    }
}