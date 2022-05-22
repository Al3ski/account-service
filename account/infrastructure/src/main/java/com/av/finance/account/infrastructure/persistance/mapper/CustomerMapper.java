package com.av.finance.account.infrastructure.persistance.mapper;

import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.infrastructure.persistance.entity.CustomerEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toCustomer(CustomerEntity entity);

    List<Customer> toCustomers(Iterable<CustomerEntity> entities);

    CustomerEntity toEntity(Customer customer);

    List<CustomerEntity> toEntities(Iterable<Customer> customers);
}
