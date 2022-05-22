package com.av.finance.account.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@Builder
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class AccountDetails {

    private final UUID accountId;

    private final String name;

    private final String surname;

    private final BigDecimal balance;

    @JsonProperty(value = "transactions")
    private final List<TxDetails> accountTransactions;
}
