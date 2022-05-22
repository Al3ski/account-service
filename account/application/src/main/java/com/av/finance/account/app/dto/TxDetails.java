package com.av.finance.account.app.dto;

import com.av.finance.account.domain.transaction.TransactionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class TxDetails {

    private final UUID accountId;
    private final TransactionType txType;
    private final BigDecimal amount;
    private final String details;
}
