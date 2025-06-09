package com.nttdata.technicaltest.services.infrastructure.output.repository.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;

@Table("person")
@lombok.Getter
@lombok.Setter
@NoArgsConstructor
@AllArgsConstructor
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
