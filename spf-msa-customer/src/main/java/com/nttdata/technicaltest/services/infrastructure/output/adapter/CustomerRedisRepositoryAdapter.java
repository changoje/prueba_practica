package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.config.PropertiesConfiguration;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.domain.dto.CustomerCacheDto;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.CustomerConstants;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.JsonSerializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CustomerRedisRepositoryAdapter implements CustomerRedisRepositoryPort {
    private final PropertiesConfiguration properties;
    private final ReactiveRedisTemplate<String, String> reactiveRedisTemplate;

    public Mono<Void> sendDataToAsync(CustomerCacheDto customerCacheDto) {
        String data = JsonSerializer.jsonObjectToString(customerCacheDto);
        return reactiveRedisTemplate.convertAndSend("message", data)
                .doOnSuccess(success -> log.info("Send data to Async for create account -> {}", customerCacheDto.getDocumentNumber()))
                .doOnError(error -> log.error("Error, Send data to Async for create account  -> {}", error.getMessage()))
                .then(reactiveRedisTemplate.opsForHash().put(CustomerConstants.CUSTOMER_KEY, customerCacheDto.getDocumentNumber(), data))
                .then();
    }

    public Mono<Void> saveGetCustomer(CustomerCacheDto customerDto) {
        String data = JsonSerializer.jsonObjectToString(customerDto);
        return reactiveRedisTemplate.opsForHash().put(CustomerConstants.CUSTOMER_KEY, customerDto.getDocumentNumber(), data)
                .then(reactiveRedisTemplate.expire(CustomerConstants.CUSTOMER_KEY, Duration.ofMinutes(Integer.parseInt(properties.getTimeSession()))))
                .then();
    }
}
