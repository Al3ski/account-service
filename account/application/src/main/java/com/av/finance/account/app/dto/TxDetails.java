package com.av.finance.account.app.dto;

import com.av.finance.account.domain.transaction.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class TxDetails {

    UUID txId;

    UUID accountId;

    TransactionType txType;

    BigDecimal amount;

    String details;
}
