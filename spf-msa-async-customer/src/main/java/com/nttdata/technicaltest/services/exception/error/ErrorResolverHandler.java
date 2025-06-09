package com.nttdata.technicaltest.services.exception.error;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.technicaltest.services.exception.*;
import com.nttdata.technicaltest.services.exception.error.resolver.ConflictExceptionResolver;
import com.nttdata.technicaltest.services.exception.error.resolver.ErrorResolver;
import com.nttdata.technicaltest.services.exception.error.resolver.NotFoundErrorResolver;
import com.nttdata.technicaltest.services.exception.error.resolver.UnexpectedErrorResolver;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@Slf4j
public class ErrorResolverHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper mapper;
    private final UnexpectedErrorResolver unexpectedErrorResolver = new UnexpectedErrorResolver();
    private final NotFoundErrorResolver notFoundErrorResolver = new NotFoundErrorResolver();
    private final Map<Class<? extends Throwable>, ErrorResolver<?>> resolvers;

    @PostConstruct
    private void initializeResolvers() {
        resolvers.put(ClientAccountOperationException.class, new ConflictExceptionResolver());
        resolvers.put(ClientNotFoundException.class, notFoundErrorResolver);
    }

    @SafeVarargs
    @NonNull
    @SuppressWarnings({"unchecked", "rawtypes"})
    private ErrorResolver<?> getFallbackErrorResolver(
            @NonNull final Throwable throwable,
            @NonNull final Class<? extends Throwable>... classes
    ) {
        return Stream.of(classes)
                .filter(theClass -> theClass.isInstance(throwable))
                .findFirst()
                .map(resolvers::get)
                .orElse((ErrorResolver) unexpectedErrorResolver);
    }

    @NonNull
    @Override
    public Mono<Void> handle(@NonNull final ServerWebExchange serverWebExchange, @NonNull final Throwable throwable) {
        return Mono.just(serverWebExchange.getResponse())
                .doOnNext(response -> response.getHeaders().setContentType(MediaType.APPLICATION_JSON))
                .map(response ->
                        Tuples.of(
                                response,
                                resolvers.getOrDefault(throwable.getClass(), getFallbackErrorResolver(throwable, CodeException.class))
                        )
                )
                .flatMap(responseAndResolverTuple ->
                        responseAndResolverTuple.getT1().writeWith(
                                Mono.fromCallable(() ->
                                                mapper.writeValueAsBytes(responseAndResolverTuple.getT2().apply(serverWebExchange, throwable, "1.0.1"))
                                        )
                                        .doOnNext(error -> log.error("Error response: {}", new String(error, StandardCharsets.UTF_8)))
                                        .map(responseAndResolverTuple.getT1().bufferFactory()::wrap)
                        )
                );
    }
}
