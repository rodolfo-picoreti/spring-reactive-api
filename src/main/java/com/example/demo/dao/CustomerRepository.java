package com.example.demo.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    @Query("""
           select count(1) from t_customer c 
           where (:email = '' or c.email like :email)
             and (:ssn = '' or c.ssn like :ssn)
           """)
    Mono<Long> countWithFilters(String email, String ssn);

    @Query("""
           select c.* from t_customer c 
           where (:email = '' or c.email like :email)
             and (:ssn = '' or c.ssn like :ssn)
           limit :limit
           offset :offset
           """)
    Flux<Customer> findWithFilters(String email, String ssn, long limit, long offset);

}
