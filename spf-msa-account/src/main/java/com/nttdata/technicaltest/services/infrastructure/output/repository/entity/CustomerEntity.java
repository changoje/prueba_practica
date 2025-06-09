package com.nttdata.technicaltest.services.infrastructure.output.repository.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Table("customer")
@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerEntity {
    @Id
    @Column("customer_id")
    private Long customerId;
    @Column("password")
    private String password;
    @Column("status")
    private String status;
    @Column("person_id")
    private Long personId;
}
