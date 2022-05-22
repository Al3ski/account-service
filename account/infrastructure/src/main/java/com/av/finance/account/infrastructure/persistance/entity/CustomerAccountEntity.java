package com.av.finance.account.infrastructure.persistance.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CUSTOMER_ACCOUNTS")
public class CustomerAccountEntity {

    @Id
    private UUID accountId;

    private UUID customerId;

    private String accountType;

    private BigDecimal balance;
}
