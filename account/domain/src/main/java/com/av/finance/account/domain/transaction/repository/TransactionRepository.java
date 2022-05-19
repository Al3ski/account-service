package com.av.finance.account.domain.transaction.repository;

import com.av.finance.account.domain.transaction.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    Transaction retrieve(UUID txId);

    List<Transaction> retrieveByAccount(UUID accountId);

    void save(Transaction transaction);
}
