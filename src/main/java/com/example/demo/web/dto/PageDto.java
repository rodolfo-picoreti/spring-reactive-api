package com.example.demo.web.dto;

import lombok.Builder;
import lombok.Getter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@Builder
public class PageDto<T> {

    private long thisPage;
    private long lastPage;
    private long totalRecords;

    private List<T> records;

}
