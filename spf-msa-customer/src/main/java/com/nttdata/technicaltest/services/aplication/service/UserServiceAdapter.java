package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.infrastructure.exception.ValidateSessionException;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.PasswordService;
import com.nttdata.technicaltest.services.aplication.input.port.UserService;
import com.nttdata.technicaltest.services.aplication.input.port.UserSessionService;
import com.nttdata.technicaltest.services.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceAdapter implements UserService {
    private final UserSessionService userSessionService;
    private final CustomerRepositoryPort customerRepositoryPort;
    private final PasswordService passwordService;

    @Override
    public Mono<String> login(UserDto userDTO) {
        return validateUser(userDTO)
                .flatMap(isValidSession -> {
                    boolean isValid = Boolean.TRUE.equals(isValidSession);
                    if (isValid) {
                        return handleSessionLogin(userDTO);
                    } else {
                        return Mono.error(new ValidateSessionException());
                    }
                })
                .doOnSuccess(success -> log.info("Success, the credentials OK"))
                .doOnError(error -> log.error("Error, Validate the credentials!! -> {}", error.getMessage()));
    }

    private Mono<String> handleSessionLogin(UserDto userDTO) {
        String username = userDTO.getUsername();
        log.info("|--> Login process started: username={}", username);

        return userSessionService.login(userDTO)
                .doOnSuccess(token -> log.info("<--| Login process finished successfully: username={}", username))
                .doOnError(error -> log.error("<--| Login process finished with error: username={}", username));
    }

    private Mono<Boolean> validateUser(UserDto userDTO) {
        return customerRepositoryPort.validUser(userDTO.getUsername())
                .switchIfEmpty(Mono.error(new ValidateSessionException()))
                .map(customerEntityDTO -> passwordService.matchesPassword(
                        userDTO.getPassword(),
                        customerEntityDTO.getPassword()
                ));
    }
}