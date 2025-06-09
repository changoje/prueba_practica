package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper;

import com.nttdata.technicaltest.services.model.PostCustomerCreateRequest;
import com.nttdata.technicaltest.services.model.PutCustomerRequest;
import com.nttdata.technicaltest.services.domain.dto.PostCustomerDto;
import com.nttdata.technicaltest.services.domain.dto.PutCustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ControllerCustomerMapper {

    ControllerCustomerMapper INSTANCE = Mappers.getMapper(ControllerCustomerMapper.class);

    @Mapping(target = "genderTypeEnum", source = "gender")
    @Mapping(target = "accountTypeEnum", source = "accountType")
    PostCustomerDto mapperToCustomerPostDTO(PostCustomerCreateRequest postCustomerCreateRequest);

    @Mapping(target = "genderTypeEnum", source = "gender")
    PutCustomerDto mapperToCustomerPutDTO(PutCustomerRequest putCustomerRequest);
}
