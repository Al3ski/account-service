package com.av.finance.account.infrastructure.persistance.account;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface CustomerAccountDao extends CrudRepository<CustomerAccountEntity, UUID> {

    List<CustomerAccountEntity> findAllByCustomerId(UUID customerId);

    List<CustomerAccountEntity> findAllByCustomerIdIn(Iterable<UUID> customerIds);
}
