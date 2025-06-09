package com.nttdata.technicaltest.services.domain.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntityDto {
    private Long personId;
    private String address;
    private String age;
    private String gender;
    private String documentNumber;
    private String name;
    private String phone;
}
