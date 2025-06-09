package com.nttdata.technicaltest.services.domain.enums;

import lombok.Getter;

@Getter
public enum GenderTypeEnum {

    MALE("MALE"),
    FEMALE("FEMALE");

    private final String description;

    GenderTypeEnum(String description) {
        this.description = description;
    }
}
