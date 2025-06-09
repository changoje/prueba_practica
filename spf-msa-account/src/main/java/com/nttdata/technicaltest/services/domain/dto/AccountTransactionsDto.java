package com.nttdata.technicaltest.services.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@lombok.Getter
@lombok.Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionsDto {
    private Long accountTransactionId;
    private String transactionDate;
    private String transaction;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal balanceOld;
    private BigDecimal balance;
    private Long accountId;
}
