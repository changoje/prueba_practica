package com.nttdata.technicaltest.services.infrastructure.output.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("account_transaction")
@lombok.Getter
@lombok.Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AccountTransactionEntity {
    @Id
    @Column("account_transaction_id")
    private Long accountTransactionId;
    @Column("transaction_date")
    private LocalDateTime transactionDate;
    private String transaction;
    @Column("transaction_type")
    private String transactionType;
    @Column("amount")
    private BigDecimal amount;
    @Column("balance")
    private BigDecimal balance;
    @Column("account_id")
    private Long accountId;
}
