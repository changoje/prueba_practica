package com.nttdata.technicaltest.services.infrastructure.output.repository.entity;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Table("customer")
@lombok.Getter
@lombok.Setter
@Entity
public class CustomerEntity extends PersonEntity {
    @Column("customer_id")
    private Long customerId;
    @Column("password")
    private String password;
    @Column("status")
    private String status;
    @Column("user_name")
    private String userName;
}
