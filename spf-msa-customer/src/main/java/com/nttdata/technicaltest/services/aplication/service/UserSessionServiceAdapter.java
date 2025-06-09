package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.aplication.input.port.JwtService;
import com.nttdata.technicaltest.services.aplication.input.port.UserSessionService;
import com.nttdata.technicaltest.services.domain.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSessionServiceAdapter implements UserSessionService {
    private static final String UUID_CLAIM = "uuid";

    private final JwtService jwtService;

    @Override
    public Mono<String> login(UserDto userDTO) {
        return createUserClaims(userDTO).flatMap(jwtService::generateJwt)
                .doOnSuccess(token -> log.info("The user [{}] was logged in", userDTO.getUsername()))
                .doOnError(error -> log.error("Error logging in the user [{}]: error={}", userDTO.getUsername(), error.getMessage()));
    }

    private Mono<Claims> createUserClaims(UserDto userDTO) {
        return Mono.just(Jwts.claims().setSubject(userDTO.getUsername()))
                .doOnSuccess(claims -> claims.put(UUID_CLAIM, UUID.randomUUID().toString()));
    }
}
