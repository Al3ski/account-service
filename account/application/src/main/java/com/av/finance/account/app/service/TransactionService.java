package com.av.finance.account.app.service;

import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    List<TxDetails> getTransactionsForAccounts(List<UUID> accountIds);

    void createTransaction(UUID accountId, TransactionType type, BigDecimal initialCredit, String details);
}
