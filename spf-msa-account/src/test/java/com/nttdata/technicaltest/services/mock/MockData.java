package com.nttdata.technicaltest.services.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.technicaltest.services.model.*;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.AccountTransactionEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MockData {
    public static CustomerDto customerDto() {
        return CustomerDto
                .builder()
                .customerId(12L)
                .accountType("4521562456")
                .status("ENABLED")
                .documentNumber("2365897896")
                .name("Jose Ruiz")
                .initialBalance(BigDecimal.valueOf(526))
                .password("545469")
                .build();
    }

    public static PersonEntity personEntity() {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setDocumentNumber("2365897896");
        personEntity.setPersonId(12L);
        personEntity.setName("Jose Ruiz");
        personEntity.setAge("12");
        personEntity.setGender("M");
        return personEntity;
    }

    public static AccountEntity mapperToAccountEntity() {
        return AccountEntity
                .builder()
                .customerId(12L)
                .accountType("AHORRO")
                .status("ENABLED")
                .accountId(1L)
                .accountNumber("2365897896")
                .initialBalance(BigDecimal.valueOf(500))
                .availableBalance(BigDecimal.valueOf(500))
                .build();
    }

    public static AccountEntity accountDisabled() {
        return AccountEntity
                .builder()
                .customerId(12L)
                .accountType("AHORRO")
                .status("DISABLED")
                .accountId(1L)
                .accountNumber("2365897896")
                .initialBalance(BigDecimal.valueOf(500))
                .availableBalance(BigDecimal.valueOf(500))
                .build();
    }

    public static PostCustomerAccountRequest PostCustomerAccountRequest() throws JsonProcessingException {
        String object = "{\"documentNumber\":\"2365897896\",\"accountType\":\"AHORRO\",\"initialBalance\":500}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PostCustomerAccountRequest.class);
    }

    public static PatchCustomerAccountRequest PostPatchCustomerAccountRequest() throws JsonProcessingException {
        String object = "{\"status\":{\"code\":\"ENABLED\"}}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PatchCustomerAccountRequest.class);
    }

    public static AccountTransactionEntity AccountTransactionEntity() {
        return AccountTransactionEntity
                .builder()
                .accountTransactionId(1L)
                .accountId(1L)
                .transactionDate(LocalDateTime.now())
                .transaction("DEBIT")
                .transactionType("DEBIT_CARD")
                .amount(BigDecimal.valueOf(60))
                .balance(BigDecimal.valueOf(60))
                .build();
    }

    public static Transactions transactions() {
        Transactions transactions = new Transactions();
        transactions.setTransaction(Transactions.TransactionEnum.DEBIT);
        transactions.setTypeTransaction(Transactions.TypeTransactionEnum.DEBIT_CARD);
        return transactions;
    }

    public static PostCustomerTransactionsRequest PostPostCustomerTransactionsRequest() throws JsonProcessingException {
        String object = "{\"documentNumber\":\"2365897896\",\"accountNumber\":\"2254566584\",\"amount\":10,\"transactions\":{\"transaction\":\"DEBIT\",\"typeTransaction\":\"DEBIT_CARD\"}}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, PostCustomerTransactionsRequest.class);
    }

    public static GetCustomerAccountResponse getCustomerAccountResponse() throws JsonProcessingException {
        String object = "{\"customer\":{\"documentNumber\":\"0788100072\",\"fullName\":\"\"},\"accounts\":[{\"accountNumber\":\"8484012063\",\"accountType\":\"AHORRO\",\"initialBalance\":560.0,\"availableBalance\":560.0,\"status\":{\"code\":\"ENABLED\"}}]}";
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, GetCustomerAccountResponse.class);
    }
}
