package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.application.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.domain.dto.CustomerDto;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.config.PropertiesConfiguration;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.Constants;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.JsonSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerRedisRepositoryAdapter implements CustomerRedisRepositoryPort {

    private final PropertiesConfiguration properties;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;


    @Override
    public Mono<Void> deleteCustomer() {
        return reactiveRedisTemplate
                .opsForHash()
                .delete(Constants.CUSTOMER_KEY)
                .doOnSuccess(success -> log.info("Success, to delete data of customer cache."))
                .doOnError(error -> log.error("Error, to delete data of customer cache. -> {}", error.getMessage()))
                .then();
    }

    @Override
    public Mono<CustomerDto> find(String uuid) {
        return reactiveRedisTemplate
                .opsForHash()
                .get(Constants.CUSTOMER_KEY, uuid)
                .map(o -> JsonSerializer.jsonStringToObject(o.toString(), CustomerDto.class))
                .doOnSuccess(success -> log.info("Success, to get data of customer cache."))
                .doOnError(error -> log.error("Error, to get data of customer cache. -> {}", error.getMessage()));
    }
}
