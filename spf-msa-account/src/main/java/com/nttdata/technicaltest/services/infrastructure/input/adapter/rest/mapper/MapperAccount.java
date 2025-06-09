package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper;

import com.nttdata.technicaltest.services.model.Account;
import com.nttdata.technicaltest.services.model.GetCustomerAccountResponse;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface MapperAccount {
    MapperAccount INSTANCE = Mappers.getMapper(MapperAccount.class);

    @Mapping(target = "status", constant = "ENABLED")
    @Mapping(target = "accountNumber", source = "accountNumber")
    @Mapping(target = "initialBalance", source = "balance")
    @Mapping(target = "availableBalance", source = "balance")
    @Mapping(target = "accountId", ignore = true)
    AccountEntity mapperToAccount(String accountNumber, BigDecimal balance, String accountType, Long customerId);

    @Mapping(target = "status.code", source = "accountEntity.status")
    Account mapperToAccount(AccountEntity accountEntity);

    @Mapping(target = "customer.documentNumber", source = "customerDto.documentNumber")
    @Mapping(target = "customer.fullName", source = "customerDto.name")
    @Mapping(target = "accounts", source = "accounts")
    GetCustomerAccountResponse mapperToGetCustomerAccountResponse(CustomerDto customerDto, List<AccountEntity> accounts);

}
