package com.av.finance.account.infrastructure.persistance.mapper;

import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.infrastructure.persistance.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerEntity entity);

    CustomerEntity toEntity(Customer customer);
}
