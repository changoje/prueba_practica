package com.nttdata.technicaltest.services.aplication.input.port;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

public interface JwtService {
    Mono<String> generateJwt(Claims claims);
    Mono<Claims> getClaimsFromToken(String token);
}
