package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper;

import com.nttdata.technicaltest.services.domain.dto.*;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import com.nttdata.technicaltest.services.model.GetCustomerSearchResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "status.code", source = "status")
    GetCustomerSearchResponse mapperToCustomer(PersonEntityDto personEntityDto, String status);

    @Mapping(target = "personId", ignore = true)
    @Mapping(target = "gender", expression = "java(postCustomerDto.getGenderTypeEnum().getDescription())")
    PersonEntity mapperToPersonEntity(PostCustomerDto postCustomerDto);

    @Mapping(target = "documentNumber", source = "personEntityDto.documentNumber")
    @Mapping(target = "name", source = "personEntityDto.name")
    @Mapping(target = "initialBalance", expression = "java(postCustomerDto.getInitialBalance())")
    @Mapping(target = "accountType", expression = "java(postCustomerDto.getAccountTypeEnum().getValue())")
    @Mapping(target = "password", ignore = true)
    CustomerCacheDto mapperToCustomerCacheDto(PersonEntityDto personEntityDto, CustomerEntityDto customerDto, PostCustomerDto postCustomerDto);

    @Mapping(target = "personId", source = "personEntityDto.personId")
    @Mapping(target = "documentNumber", source = "personEntityDto.documentNumber")
    @Mapping(target = "address", source = "putCustomerDto.address")
    @Mapping(target = "gender", expression = "java(putCustomerDto.getGenderTypeEnum().getDescription())")
    @Mapping(target = "name", source = "putCustomerDto.name")
    @Mapping(target = "age", source = "putCustomerDto.age")
    @Mapping(target = "phone", source = "putCustomerDto.phone")
    PersonEntity mapperToPutCustomerRequest(PersonEntityDto personEntityDto, PutCustomerDto putCustomerDto);

    PersonEntityDto mapperToPersonDto(PersonEntity personEntity);
}
