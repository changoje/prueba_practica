package com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.impl;

import com.nttdata.technicaltest.services.model.PostUserLoginRequest;
import com.nttdata.technicaltest.services.model.PostUserLoginResponse;
import com.nttdata.technicaltest.services.server.UsersApi;
import com.nttdata.technicaltest.services.aplication.input.port.UserService;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UsersApi {

    private final UserService userService;
    @Override
    public Mono<ResponseEntity<PostUserLoginResponse>> postUserLogin(Mono<PostUserLoginRequest> postUserLoginRequest, ServerWebExchange exchange) {
        return postUserLoginRequest.flatMap(request -> userService.login(UserMapper.INSTANCE.mapperToUser(request))
                .map(UserMapper.INSTANCE::mapperToPostUserLoginResponse)
                .map(ResponseEntity::ok));
    }
}
