package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util;


import com.nttdata.technicaltest.services.domain.ParametersBuilder;

public class BuilderObject {
    private BuilderObject() {
    }

    public static ParametersBuilder parametersBuilder(String documentNumber, String accountType, String startDate, String endDate) {
        return new ParametersBuilder
                .Builder()
                .withDocumentNumber(documentNumber)
                .withAccountType(accountType)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();
    }
}
