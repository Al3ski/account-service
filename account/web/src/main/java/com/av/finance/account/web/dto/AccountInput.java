package com.av.finance.account.web.dto;

import com.av.finance.account.domain.account.CustomerAccountType;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Value
public class AccountInput {

    @NotNull
    UUID customerId;

    @NotNull
    CustomerAccountType accountType;

    @DecimalMin("0")
    BigDecimal initialCredit;

}
