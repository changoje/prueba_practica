package com.nttdata.technicaltest.services.domain.dto;

import lombok.*;

import java.math.BigDecimal;


@Builder
@lombok.Getter
@lombok.Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long customerId;
    private String documentNumber;
    private String name;
    private String password;
    private BigDecimal initialBalance;
    private String accountType;
    private String status;
}
