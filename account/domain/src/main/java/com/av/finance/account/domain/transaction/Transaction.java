package com.av.finance.account.domain.transaction;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class Transaction {

    private UUID txId;
    private UUID accountId;
    private String details;
    private TransactionType txType;
    private BigDecimal amount;

    private Transaction(UUID txId, UUID accountId, TransactionType type, BigDecimal amount, String details) {
        if (txId == null) {
            throw new TransactionValidationError("Transaction id can't be null");
        }
        if (accountId == null) {
            throw new TransactionValidationError("Account id can't be null");
        }
        if (type == null) {
            throw new TransactionValidationError("Transaction type required");
        }
        if (amount == null) {
            throw new TransactionValidationError("Transaction amount required");
        }
        this.txId = txId;
        this.accountId = accountId;
        this.txType = type;
        this.amount = amount;
        this.details = details;
    }

    Transaction(TransactionBuilder builder) {
        this(builder.txId, builder.accountId, builder.txType, builder.amount, builder.details);
    }

    public Transaction create(UUID accountId, TransactionType type, BigDecimal amount, String details) {
        return new Transaction(UUID.randomUUID(), accountId, type, amount, details);
    }

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(txId, that.txId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(details, that.details) &&
                txType == that.txType &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(txId, accountId, details, txType, amount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Transaction.class.getSimpleName() + "[", "]")
                .add("txId=" + txId)
                .add("accountId=" + accountId)
                .add("details='" + details + "'")
                .add("txType=" + txType)
                .add("amount=" + amount)
                .toString();
    }
}
