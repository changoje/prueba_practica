package com.nttdata.technicaltest.services.aplication.service;

import com.nttdata.technicaltest.services.domain.dto.PersonEntityDto;
import com.nttdata.technicaltest.services.model.GetCustomerSearchResponse;
import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.CustomerSearchService;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.PersonEntityRepository;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.PersonEntity;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.mapper.CustomerMapper;
import com.nttdata.technicaltest.services.infrastructure.input.adapter.rest.util.CustomerConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerSearchServiceAdapter implements CustomerSearchService {
    private final PersonEntityRepository personEntityRepository;
    private final CustomerRepositoryPort customerRepositoryPort;

    @Override
    public Flux<GetCustomerSearchResponse> getCustomerSearch(String documentNumber) {
        return documentNumber.isEmpty() ? processGetAllCustomers() : processGetCustomer(documentNumber);
    }

    private Flux<GetCustomerSearchResponse> processGetCustomer(String documentNumber) {
        return findCustomerByDocumentNumber(documentNumber)
                .flatMapMany(personEntityDTO -> retrieveCustomersForPerson(personEntityDTO)
                        .flatMapMany(customerDto -> Flux.just(CustomerMapper.INSTANCE.mapperToCustomer(personEntityDTO, customerDto.getStatus()))));
    }

    private Mono<PersonEntityDto> findCustomerByDocumentNumber(String documentNumber) {
        return personEntityRepository.findByDocumentNumber(documentNumber)
                .doOnSuccess(success -> log.info("Successfully, to find Customer By Document Number."))
                .doOnError(error -> log.error("Error, to find Customer By Document Number.: {}", error.getMessage()));
    }

    private Flux<GetCustomerSearchResponse> processGetAllCustomers() {
        return getAllCustomers()
                .map(CustomerMapper.INSTANCE::mapperToPersonDto)
                .flatMap(personEntityDTO -> retrieveCustomersForPerson(personEntityDTO)
                        .map(customerDto -> CustomerMapper.INSTANCE.mapperToCustomer(personEntityDTO, customerDto.getStatus())));
    }

    private Flux<PersonEntity> getAllCustomers() {
        return personEntityRepository
                .findAll()
                .doOnComplete(() -> log.info("Successfully, to get all customers."))
                .doOnError(error -> log.error("Error, to get all customers: {}", error.getMessage()));
    }

    private Mono<CustomerEntityDto> retrieveCustomersForPerson(PersonEntityDto personEntityDto) {
        return customerRepositoryPort.findByPersonIdAndStatus(personEntityDto.getPersonId(), CustomerConstants.CUSTOMER_STATUS)
                .doOnSuccess(success -> log.info("Successfully retrieved customer data."))
                .doOnError(error -> log.error("Error retrieving customer data: {}", error.getMessage()));
    }
}
