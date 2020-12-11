package com.example.demo.web.api;


import com.example.demo.dao.Customer;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.web.dto.*;
import com.example.demo.web.exception.GlobalExceptionHandler;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @GetMapping
    public Flux<PageDto<CustomerReadDto>> getCustomers(CustomerFiltersDto filters) {
        final long offset = filters.getLimit() * filters.getPage();
        final long limit = filters.getLimit();

        final var totalRecords = customerRepository.countWithFilters(filters.getEmail(), filters.getSsn());
        final var records = customerRepository.findWithFilters(filters.getEmail(), filters.getSsn(), limit, offset)
                .map(this::toDto)
                .collectList();

        return Flux.zip(records, totalRecords,
                (r, t) -> PageDto.<CustomerReadDto>builder()
                        .records(r)
                        .totalRecords(t)
                        .thisPage(filters.getPage())
                        .lastPage(t / filters.getLimit())
                        .build());
    }

    @GetMapping("/{id}")
    public Mono<CustomerReadDto> getCustomer(@PathVariable(name = "id") Long id) {
        return customerRepository.findById(id)
                .switchIfEmpty(GlobalExceptionHandler.resourceNotFound())
                .map(this::toDto);
    }

    @PostMapping
    public Mono<CustomerReadDto> createCustomer(@Valid @RequestBody CustomerCreateDto dto) {
        final var customer = modelMapper.map(dto, Customer.class);

        return customerRepository.save(customer)
                .map(this::toDto);
    }

    @PutMapping("/{id}")
    public Mono<CustomerReadDto> updateCustomer(@PathVariable(name = "id") Long id,
                                                @RequestBody CustomerUpdateDto dto) {
        return customerRepository.findById(id)
                .switchIfEmpty(GlobalExceptionHandler.resourceNotFound())
                .flatMap(c -> {
                    modelMapper.map(dto, c);
                    return customerRepository.save(c);
                }).map(this::toDto);
    }

    private CustomerReadDto toDto(Customer customer) {
        return modelMapper.map(customer, CustomerReadDto.class);
    }
}
