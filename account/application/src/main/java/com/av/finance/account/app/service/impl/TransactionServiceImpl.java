package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.rest.RestClient;
import com.av.finance.account.app.service.TransactionService;
import com.av.finance.account.domain.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final RestClient restClient;

    @Override
    public UUID createTransaction(UUID customerId, TransactionType type,
                                  BigDecimal initialCredit, String details) {
        final UUID uuid = restClient.postRetryable("", null, null);
        log.info("Initial credit transaction successfully processed for customer: {}", customerId);
        return UUID.randomUUID();
    }
}
