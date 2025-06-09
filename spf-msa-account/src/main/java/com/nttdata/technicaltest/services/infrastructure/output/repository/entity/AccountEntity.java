package com.nttdata.technicaltest.services.infrastructure.output.repository.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("account")
@Builder
@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    @Column("account_id")
    private Long accountId;
    @Column("status")
    private String status;
    @Column("account_number")
    private String accountNumber;
    @Column("available_balance")
    private BigDecimal availableBalance;
    @Column("initial_balance")
    private BigDecimal initialBalance;
    @Column("account_type")
    private String accountType;
    @Column("customer_id")
    private Long customerId;
}
