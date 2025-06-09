package com.nttdata.technicaltest.services.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutCustomerDto extends CustomerDto {
    private StatusDto status;
}
