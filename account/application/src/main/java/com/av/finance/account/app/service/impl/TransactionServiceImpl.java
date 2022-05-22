package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.app.service.TransactionService;
import com.av.finance.account.domain.transaction.Transaction;
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

    private final String transactionUrl = "http://localhost:8092/v1/transactions";

    private final RestClient restClient;

    @Override
    public UUID createTransaction(UUID accountId, TransactionType type,
                                  BigDecimal initialCredit, String details) {
        final Transaction transaction = Transaction.create(accountId, type, initialCredit, details);
        final UUID transactionId = restClient.postRetryable(transactionUrl, new HttpEntity<>(transaction), UUID.class);
        log.info("Initial credit transaction successfully processed for account: {}", accountId);

        return transactionId;
    }
}
