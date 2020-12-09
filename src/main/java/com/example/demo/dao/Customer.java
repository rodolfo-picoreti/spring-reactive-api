package com.example.demo.dao;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table("t_customer")
public class Customer {
    @Id
    private Long id;

    private String ssn;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String gender;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Version
    private Long version;
}