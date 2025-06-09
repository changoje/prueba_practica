package com.nttdata.technicaltest.services.domain.dto;

import com.nttdata.technicaltest.services.domain.enums.AccountTypeEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCustomerDto extends CustomerDto {
    @NotNull(message = "The documentNumber cannot be null.")
    private String documentNumber;

    @NotNull(message = "The userName cannot be null.")
    private String userName;

    @NotNull(message = "Account Type ENUM must be selected (AHORRO/CREDITO)")
    private AccountTypeEnum accountTypeEnum;

    @NotNull(message = "The initial balance cannot be null.")
    @Positive(message = "The initial balance must be greater than zero.")
    @DecimalMin(value = "0.01", message = "The initial balance must be at least 0.01.")
    private BigDecimal initialBalance;
}
