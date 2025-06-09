package com.nttdata.technicaltest.services.domain;

@lombok.Getter
public class ParametersBuilder {
    private final String documentNumber;
    private final String accountType;
    private final String startDate;
    private final String endDate;

    private ParametersBuilder(Builder builder) {
        this.documentNumber = builder.documentNumber;
        this.accountType = builder.accountType;
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
    }

    public static class Builder {
        private String documentNumber;
        private String accountType;
        private String startDate;
        private String endDate;

        public Builder withDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        public Builder withAccountType(String accountType) {
            this.accountType = accountType;
            return this;
        }

        public Builder withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public ParametersBuilder build() {
            return new ParametersBuilder(this);
        }
    }
}
