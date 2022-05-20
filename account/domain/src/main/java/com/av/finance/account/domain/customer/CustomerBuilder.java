package com.av.finance.account.domain.customer;

import java.util.UUID;

public class CustomerBuilder {

    UUID customerId;
    String name;
    String surname;

    public CustomerBuilder customerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder surname(String surname) {
        this.surname = surname;
        return this;
    }

    public Customer build() {
        return new Customer(this);
    }
}
