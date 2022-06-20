package com.av.finance.account.web.dto;

import com.av.finance.account.domain.account.CustomerAccountType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@JsonPropertyOrder({
        "customerId",
        "accountType",
        "initialCredit"
})
@Value
public class AccountInput {

    @NotNull
    UUID customerId;

    @NotNull
    CustomerAccountType accountType;

    @DecimalMin("0")
    BigDecimal initialCredit;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AccountInput(@JsonProperty("customerId") UUID customerId,
                        @JsonProperty("accountType") CustomerAccountType accountType,
                        @JsonProperty("initialCredit") BigDecimal initialCredit) {
        this.customerId = customerId;
        this.accountType = accountType;
        this.initialCredit = initialCredit;
    }

}
