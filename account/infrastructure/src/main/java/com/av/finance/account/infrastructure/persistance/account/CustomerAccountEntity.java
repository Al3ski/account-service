package com.av.finance.account.infrastructure.persistance.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
    @Column(updatable = false, nullable = false)
    private UUID accountId;

    @Column(updatable = false, nullable = false)
    private UUID customerId;

    @Column(updatable = false, nullable = false)
    private String accountType;

    @Column(nullable = false, precision = 14, scale = 5)
    private BigDecimal balance;
}
