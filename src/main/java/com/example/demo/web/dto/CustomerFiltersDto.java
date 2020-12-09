package com.example.demo.web.dto;

import lombok.Getter;

@Getter
public class CustomerFiltersDto extends PaginationFiltersDto {

    private final String email;

    public CustomerFiltersDto(Integer page, Integer limit, String email) {
        super(page, limit);
        this.email = email;
    }
}
