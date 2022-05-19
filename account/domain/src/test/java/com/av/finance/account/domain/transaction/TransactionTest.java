package com.av.finance.account.domain.transaction;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.av.finance.account.domain.transaction.TransactionType.INITIAL;

public class TransactionTest {

    @Test
    public void createTransaction_success() {
        final UUID accountId = UUID.randomUUID();
        final Transaction transaction = Transaction.create(accountId, INITIAL, BigDecimal.ZERO, "");

        Assert.assertNotNull(transaction);
        Assert.assertEquals(accountId, transaction.getAccountId());
        Assert.assertEquals(INITIAL, transaction.getTxType());
        Assert.assertEquals(BigDecimal.ZERO, transaction.getAmount());
        Assert.assertEquals("", transaction.getDetails());
    }

    @Test(expected = TransactionValidationError.class)
    public void createTransaction_fail() {
        Transaction.create(UUID.randomUUID(), INITIAL, null, "");
    }

    @Test
    public void buildTransaction_success() {
        final UUID txId = UUID.randomUUID();
        final UUID accountId = UUID.randomUUID();
        final Transaction transaction = Transaction.builder()
                .txId(txId)
                .accountId(accountId)
                .txType(INITIAL)
                .amount(BigDecimal.ZERO)
                .details("")
                .build();

        Assert.assertNotNull(transaction);
        Assert.assertEquals(txId, transaction.getTxId());
        Assert.assertEquals(accountId, transaction.getAccountId());
        Assert.assertEquals(INITIAL, transaction.getTxType());
        Assert.assertEquals(BigDecimal.ZERO, transaction.getAmount());
        Assert.assertEquals("", transaction.getDetails());
    }

    @Test(expected = TransactionValidationError.class)
    public void buildTransaction_fail() {
        Transaction.builder()
                .accountId(UUID.randomUUID())
                .txType(INITIAL)
                .amount(BigDecimal.ZERO)
                .details("")
                .build();
    }
}