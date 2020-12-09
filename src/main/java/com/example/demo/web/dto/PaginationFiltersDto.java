package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;

@Getter
public class PaginationFiltersDto {

    private final int page ;

    private final int limit ;

    public PaginationFiltersDto(Integer page, Integer limit) {
        this.page = page == null ? 0 : page;
        this.limit = limit == null ? 10 : limit;
    }

    public PageRequest toPageable() {
        return PageRequest.of(page, limit);
    }

}