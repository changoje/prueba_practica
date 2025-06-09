package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.infrastructure.exception.CustomerExistException;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRedisRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.JwtService;
import com.nttdata.technicaltest.services.aplication.input.port.PersonCustomerService;
import com.nttdata.technicaltest.services.domain.dto.CustomerCacheDto;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.domain.dto.PostCustomerDto;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerCreationService;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.CustomerMapper;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.CustomerConstants;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerCreationServiceAdapter implements CustomerCreationService {
    private final PersonCustomerService personCustomerService;
    private final CustomerRedisRepositoryPort customerRedisRepositoryPort;
    private final TransactionalOperator transactionalOperator;
    private final JwtService jwtService;

    @Override
    public Mono<Void> postCustomerCreate(PostCustomerDto postCustomerDto, String authorization) {
        return transactionalOperator.transactional(
                        processCustomerCreation(postCustomerDto, authorization))
                .doOnError(error -> log.error("Transaction failed, performing rollback: {}", error.getMessage()));
    }

    private Mono<Void> processCustomerCreation(PostCustomerDto postCustomerDto, String authorization) {
        return getClaimsFromToken(authorization)
                .flatMap(claims -> validateUser(claims.getSubject())
                        .flatMap(customerEntityDto -> handlePersonCreation(postCustomerDto)));
    }

    private Mono<Void> handlePersonCreation(PostCustomerDto postCustomerDto) {
        return findPersonByDocumentNumber(postCustomerDto.getDocumentNumber())
                .flatMap(personDto -> Mono.error(new CustomerExistException()))
                .switchIfEmpty(createPersonAndCustomer(postCustomerDto))
                .then();
    }

    private Mono<Void> createPersonAndCustomer(PostCustomerDto postCustomerDto) {
        return savePerson(CustomerMapper.INSTANCE.mapperToPersonEntity(postCustomerDto))
                .map(CustomerMapper.INSTANCE::mapperToPersonDto)
                .flatMap(personDto -> saveCustomer(customerEntity(postCustomerDto, personDto.getPersonId()))
                        .then(sendEventToCreateAccount(personDto, postCustomerDto)));
    }

    private Mono<Void> sendEventToCreateAccount(PersonEntityDto personEntityDto, PostCustomerDto postCustomerDto) {
        return findCustomerByPersonId(personEntityDto.getPersonId())
                .flatMap(customerDto -> processEvent(personEntityDto, postCustomerDto, customerDto));
    }

    private Mono<PersonEntityDto> findPersonByDocumentNumber(String documentNumber) {
        return personCustomerService.findPersonByDocumentNumber(documentNumber);
    }

    private Mono<PersonEntity> savePerson(PersonEntity personEntity) {
        return personCustomerService.savePerson(personEntity);
    }

    private Mono<Void> saveCustomer(CustomerEntity customerEntity) {
        return personCustomerService.saveCustomer(customerEntity);
    }

    private Mono<CustomerEntityDto> findCustomerByPersonId(Long personId) {
        return personCustomerService.findCustomerByPersonId(personId);
    }

    private Mono<Void> processEvent(PersonEntityDto personEntityDto, PostCustomerDto postCustomerDto, CustomerEntityDto customerDto) {
        return sendToEvent(personEntityDto, postCustomerDto, customerDto)
                .then(saveCustomerCache(personEntityDto, postCustomerDto, customerDto));
    }

    private Mono<Void> sendToEvent(PersonEntityDto personEntityDto, PostCustomerDto postCustomerDto, CustomerEntityDto customerDto) {
        return customerRedisRepositoryPort.sendDataToAsync(createCustomerCacheDto(personEntityDto, customerDto, postCustomerDto));
    }

    private Mono<Void> saveCustomerCache(PersonEntityDto personEntityDto, PostCustomerDto postCustomerDto, CustomerEntityDto customerDto) {
        return customerRedisRepositoryPort.saveGetCustomer(createCustomerCacheDto(personEntityDto, customerDto, postCustomerDto));
    }

    private Mono<CustomerEntityDto> validateUser(String userName) {
        return personCustomerService.validateUser(userName);
    }

    private CustomerCacheDto createCustomerCacheDto(PersonEntityDto personEntityDto, CustomerEntityDto customerDto, PostCustomerDto postCustomerDto) {
        return CustomerMapper.INSTANCE.mapperToCustomerCacheDto(personEntityDto, customerDto, postCustomerDto);
    }

    private CustomerEntity customerEntity(PostCustomerDto postCustomerDto, Long personId) {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setPassword(postCustomerDto.getPassword());
        customerEntity.setStatus(CustomerConstants.CUSTOMER_STATUS);
        customerEntity.setPersonId(personId);
        customerEntity.setUserName(postCustomerDto.getUserName());
        return customerEntity;
    }

    private Mono<Claims> getClaimsFromToken(String token) {
        return jwtService.getClaimsFromToken(token);
    }

}
