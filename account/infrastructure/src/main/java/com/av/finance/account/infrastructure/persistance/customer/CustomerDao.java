package com.av.finance.account.infrastructure.persistance.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface CustomerDao extends CrudRepository<CustomerEntity, UUID> {
}
