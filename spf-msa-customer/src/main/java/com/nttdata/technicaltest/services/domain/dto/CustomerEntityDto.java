package com.nttdata.technicaltest.services.domain.dto;

import lombok.*;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntityDto {
    private Long customerId;
    private String password;
    private String status;
    private Long personId;
}
