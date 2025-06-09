package com.nttdata.technicaltest.services.aplication.input.port;

import com.nttdata.technicaltest.services.domain.dto.UserDto;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<String> login(UserDto userDTO);
}
