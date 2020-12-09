package com.example.demo.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

    Flux<Customer> findByIdNotNull(Pageable pageable);

    Flux<Customer> findByEmailContains(String email, Pageable pageable);
}
