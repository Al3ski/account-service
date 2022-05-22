package com.av.finance.account.infrastructure.persistance.mapper;

import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.infrastructure.persistance.entity.CustomerAccountEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerAccountMapper {

    CustomerAccount toCustomerAccount(CustomerAccountEntity entity);

    CustomerAccountEntity toEntity(CustomerAccount customerAccount);

    List<CustomerAccount> toCustomerAccounts(List<CustomerAccountEntity> entities);

    List<CustomerAccountEntity> toEntities(List<CustomerAccount> customerAccounts);
}
