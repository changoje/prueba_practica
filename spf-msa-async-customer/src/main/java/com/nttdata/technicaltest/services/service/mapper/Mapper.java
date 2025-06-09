package com.nttdata.technicaltest.services.service.mapper;

import com.nttdata.technicaltest.services.client.account.models.PostCustomerAccountRequest;
import com.nttdata.technicaltest.services.service.dto.CustomerCacheDto;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@org.mapstruct.Mapper(
        componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
public interface Mapper {
    Mapper INSTANCE = Mappers.getMapper(Mapper.class);

    PostCustomerAccountRequest mapperToPostCustomerAccountRequest(CustomerCacheDto customerDto);
}
