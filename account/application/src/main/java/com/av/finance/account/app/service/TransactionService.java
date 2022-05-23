package com.av.finance.account.app.service;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.domain.transaction.TransactionType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    List<TxDetails> getTransactionsForAccounts(List<UUID> accountIds);

    @Recover
    List<TxDetails> getTransactionsForAccountsRecover(Exception ex, List<UUID> accountIds);

    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    void createTransaction(UUID accountId, TransactionType type, BigDecimal initialCredit, String details);
}
