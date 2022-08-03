package com.av.finance.account.infrastructure.persistence.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(updatable = false, nullable = false)
    private UUID accountId;

    @Type(type = "uuid-char")
    @Column(updatable = false, nullable = false)
    private UUID customerId;

    @Column(updatable = false, nullable = false)
    private String accountType;

    @Column(nullable = false, precision = 14, scale = 5)
    private BigDecimal balance;
}
