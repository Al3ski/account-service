package com.av.finance.account.domain.customer;


import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class CustomerTest {

    @Test
    public void buildCustomer_success() {
        final UUID customerId = UUID.randomUUID();
        final Customer customer = Customer.builder()
                .customerId(customerId)
                .name("name")
                .surname("surname")
                .build();

        Assert.assertNotNull(customer);
        Assert.assertEquals(customerId, customer.getCustomerId());
        Assert.assertEquals("name", customer.getName());
        Assert.assertEquals("surname", customer.getSurname());
    }

    @Test(expected = CustomerValidationError.class)
    public void buildCustomer_fail() {
        final Customer customer = Customer.builder()
                .name("name")
                .surname("surname")
                .build();
    }

}