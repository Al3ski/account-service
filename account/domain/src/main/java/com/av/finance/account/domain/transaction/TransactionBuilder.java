package com.av.finance.account.domain.transaction;

import java.math.BigDecimal;
import java.util.UUID;

class TransactionBuilder {

    UUID txId;
    UUID accountId;
    String details;
    TransactionType txType;
    BigDecimal amount;

    public TransactionBuilder txId(UUID txId) {
        this.txId = txId;
        return this;
    }

    public TransactionBuilder accountId(UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public TransactionBuilder details(String details) {
        this.details = details;
        return this;
    }

    public TransactionBuilder txType(TransactionType txType) {
        this.txType = txType;
        return this;
    }

    public TransactionBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Transaction builder() {
        return new Transaction(this);
    }
}
