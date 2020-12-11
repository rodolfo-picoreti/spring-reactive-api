package com.example.demo.web.grpc;

import com.example.demo.customers.CustomerOuterClass;
import com.example.demo.customers.ReactorCustomerServiceGrpc;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.web.dto.CustomerReadDto;
import com.example.demo.web.dto.PageDto;
import com.salesforce.grpc.contrib.spring.GrpcService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@GrpcService
@AllArgsConstructor
public class CustomerServiceImpl extends ReactorCustomerServiceGrpc.CustomerServiceImplBase {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public Mono<CustomerOuterClass.CustomerPage> list(Mono<CustomerOuterClass.Filters> request) {
        return request.flatMap(filters -> {
            final long limit = filters.getLimit() == 0 ? 10 : filters.getLimit();
            final long offset = limit * filters.getPage();

            final var totalRecords = customerRepository.countWithFilters(filters.getEmail(), filters.getSsn());
            final var records =  customerRepository.findWithFilters(filters.getEmail(), filters.getSsn(), limit, offset)
                    .map(c -> {
                        final var builder = CustomerOuterClass.Customer.newBuilder();
                        modelMapper.map(c, builder);
                        return builder.build();
                    })
                    .collectList();

            return Flux.zip(records, totalRecords,
                    (r, t) ->  CustomerOuterClass.CustomerPage.newBuilder()
                            .addAllRecords(r)
                            .setTotalRecords(t)
                            .setThisPage(filters.getPage())
                            .setLastPage(t / limit)
                            .build())
                    .single();
        });
    }
}