package com.nttdata.technicaltest.services.domain.enums;

import lombok.Getter;

@Getter
public enum AccountTypeEnum {

    AHORRO("AHORRO"),
    CORRIENTE("CORRIENTE");

    private final String value;

    AccountTypeEnum(String description) {
        this.value = description;
    }
}
