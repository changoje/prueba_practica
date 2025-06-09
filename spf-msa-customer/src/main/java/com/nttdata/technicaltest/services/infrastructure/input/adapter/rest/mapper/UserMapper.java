package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper;

import com.nttdata.technicaltest.services.model.PostUserLoginRequest;
import com.nttdata.technicaltest.services.model.PostUserLoginResponse;
import com.nttdata.technicaltest.services.domain.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto mapperToUser(PostUserLoginRequest postUserLoginRequest);

    @Mapping(target = "token", source = "token")
    PostUserLoginResponse mapperToPostUserLoginResponse(String token);
}
