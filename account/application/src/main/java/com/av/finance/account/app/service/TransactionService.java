package com.av.finance.account.app.service;

import com.av.finance.account.domain.transaction.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {

    void createTransaction(UUID customerId, TransactionType type, BigDecimal initialCredit, String details);
}
