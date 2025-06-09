package com.nttdata.technicaltest.services.infrastructure.output.adapter;

import com.nttdata.technicaltest.services.aplication.output.port.CustomerRepositoryPort;
import com.nttdata.technicaltest.services.aplication.input.port.PasswordService;
import com.nttdata.technicaltest.services.domain.dto.CustomerEntityDto;
import com.nttdata.technicaltest.services.infrastructure.output.repository.entity.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Repository
public class CustomerRepositoryAdapter implements CustomerRepositoryPort {
    private final DatabaseClient databaseClient;
    private final PasswordService passwordService;

    public static final String PERSONID = "personId";
    public static final String STATUS = "status";
    public static final String PASSWORD = "password";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String PERSON_ID = "person_id";

    @Override
    public Mono<CustomerEntityDto> findByPersonIdAndStatus(Long personId, String status) {
        return databaseClient.sql("SELECT c.customer_id, c.password, c.status, c.person_id " +
                        "FROM customer c JOIN person p ON c.person_id = p.person_id " +
                        "WHERE c.person_id = :personId AND c.status = :status")
                .bind(PERSONID, personId)
                .bind(STATUS, status)
                .map((row, metadata) -> new CustomerEntityDto(
                        row.get(CUSTOMER_ID, Long.class),
                        row.get(PASSWORD, String.class),
                        row.get(STATUS, String.class),
                        row.get(PERSON_ID, Long.class)))
                .one();
    }

    @Override
    public Mono<CustomerEntityDto> findByPersonId(Long personId) {
        return databaseClient.sql("SELECT c.customer_id, c.password, c.status, c.person_id " +
                        "FROM customer c JOIN person p ON c.person_id = p.person_id " +
                        "WHERE c.person_id = :personId")
                .bind(PERSONID, personId)
                .map((row, metadata) -> new CustomerEntityDto(
                        row.get(CUSTOMER_ID, Long.class),
                        row.get(PASSWORD, String.class),
                        row.get(STATUS, String.class),
                        row.get(PERSON_ID, Long.class)))
                .one();
    }

    @Override
    public Mono<CustomerEntityDto> validUser(String userName) {
        return databaseClient.sql("SELECT c.customer_id, c.password, c.status, c.person_id " +
                        "FROM customer c " +
                        "WHERE " +
                        "c.user_name = :userName " +
                        "and " +
                        "c.status = :status")
                .bind("userName", userName)
                .bind(STATUS, "ENABLED")
                .map((row, metadata) -> new CustomerEntityDto(
                        row.get(CUSTOMER_ID, Long.class),
                        row.get(PASSWORD, String.class),
                        row.get(STATUS, String.class),
                        row.get(PERSON_ID, Long.class)))
                .one();
    }

    @Override
    public Mono<Void> deleteByPersonId(Long personId) {
        return databaseClient.sql("DELETE FROM customer WHERE person_id = :personId")
                .bind(PERSONID, personId)
                .then();
    }

    @Override
    public Mono<Void> saveCustomer(CustomerEntity customerEntity) {
        return databaseClient.sql("INSERT INTO customer (password, status, person_id, user_name) VALUES (:password, :status, :personId, :userName)")
                .bind(PASSWORD, passwordService.encryptPassword(customerEntity.getPassword()))
                .bind(STATUS, customerEntity.getStatus())
                .bind(PERSONID, customerEntity.getPersonId())
                .bind("userName", customerEntity.getUserName())
                .then();
    }

    @Override
    public Mono<Void> updateCustomer(String status, Long personId) {
        return databaseClient.sql("UPDATE customer SET status = :status WHERE person_id = :personId")
                .bind(STATUS, status)
                .bind(PERSONID, personId)
                .then();
    }
}
