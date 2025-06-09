package com.nttdata.technicaltest.services.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@lombok.Getter
@lombok.Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCacheDto {
    private Long customerId;
    private String documentNumber;
    private String name;
    private String password;
    private BigDecimal initialBalance;
    private String accountType;
    private String status;
}
