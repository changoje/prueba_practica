package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper;

import com.nttdata.technicaltest.services.model.Accounts;
import com.nttdata.technicaltest.services.model.CustomerTransactions;
import com.nttdata.technicaltest.services.model.Transactions;
import com.nttdata.technicaltest.services.domain.dto.AccountTransactionsDto;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface MapperTransaction {
    MapperTransaction INSTANCE = Mappers.getMapper(MapperTransaction.class);

    @Mapping(target = "transactionDate", expression = "java(java.time.LocalDateTime.now(java.time.ZoneId.of(\"UTC\")))")
    @Mapping(target = "accountTransactionId", ignore = true)
    @Mapping(target = "transaction", expression = "java(mapTransaction(transactionType))")
    @Mapping(target = "transactionType", expression = "java(mapTransactionType(transactionType))")
    AccountTransactionEntity mapperToTransactionEntity(Transactions transactionType, BigDecimal amount, Long accountId, BigDecimal balance);

    @Mapping(target = "status.code", source = "accountEntity.status")
    Accounts mapperToAccount(AccountEntity accountEntity, List<AccountTransactionsDto> transactions);

    @Mapping(target = "customer.documentNumber", source = "customerDto.documentNumber")
    @Mapping(target = "customer.fullName", source = "customerDto.name")
    CustomerTransactions mapperToCustomerTransactions(CustomerDto customerDto, List<Accounts> accounts);

    default String mapTransaction(Transactions transactionType) {
        if (transactionType == null) {
            return null;
        }
        return transactionType.getTransaction().getValue();
    }

    default String mapTransactionType(Transactions transactionType) {
        if (transactionType == null) {
            return null;
        }
        return transactionType.getTypeTransaction().getValue();
    }

    @Mapping(target = "balanceOld", source = "balance")
    AccountTransactionsDto mapperToAccountTransactionsDto(AccountTransactionEntity accountTransactionEntity, BigDecimal balance);
}
