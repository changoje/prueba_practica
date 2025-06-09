package com.nttdata.technicaltest.services.infrastructure.output.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Table("person")
@lombok.Getter
@lombok.Setter
@Entity
public class PersonEntity {
    @Id
    @Column("person_id")
    private Long personId;
    @Column("address")
    private String address;
    @Column("age")
    private String age;
    @Column("gender")
    private String gender;
    @Column("documentNumber")
    private String documentNumber;
    @Column("name")
    private String name;
    @Column("phone")
    private String phone;
}
