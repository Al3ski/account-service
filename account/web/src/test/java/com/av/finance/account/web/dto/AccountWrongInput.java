package com.av.finance.account.web.dto;

import com.av.finance.account.domain.account.CustomerAccountType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@JsonPropertyOrder({
        "customerId",
        "accountType",
        "initialCredit"
})
@Value
public class AccountWrongInput {

    @JsonProperty("customerId")
    UUID customerId;

    @JsonProperty("accountType")
    CustomerAccountType accountType;

    @JsonProperty("initialCredit")
    BigDecimal initialCredit;
}
