package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.app.service.TransactionService;
import com.av.finance.account.domain.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final String transactionUrl = "/v1/transactions";

    private final RestClient restClient;

    @Override
    public void createTransaction(UUID accountId, TransactionType type,
                                  BigDecimal initialCredit, String details) {
        final TxDetails txDetails = new TxDetails(accountId, type, initialCredit, details);
        restClient.postRetryable(transactionUrl, new HttpEntity<>(txDetails), String.class);
        log.info("Initial credit transaction successfully processed for account: {}", accountId);
    }
}
