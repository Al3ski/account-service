package com.av.finance.account.domain.customer;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class Customer {

    private final UUID customerId;
    private final String name;
    private final String surname;

    private Customer(UUID customerId, String name, String surname) {
        if (customerId == null) {
            throw new CustomerValidationError("Customer id can't be null");
        }
        if (StringUtils.isBlank(name)) {
            throw new CustomerValidationError("Customer name can't be blank");
        }
        if (StringUtils.isBlank(surname)) {
            throw new CustomerValidationError("Customer surname can't be blank");
        }
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
    }

    Customer(CustomerBuilder builder) {
        this(builder.customerId, builder.name, builder.surname);
    }

    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(surname, customer.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, name, surname);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Customer.class.getSimpleName() + "[", "]")
                .add("customerId=" + customerId)
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .toString();
    }
}
