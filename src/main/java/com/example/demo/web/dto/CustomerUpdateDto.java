package com.example.demo.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateDto {

    private String firstName;

    private String lastName;

    private String gender;

    private String phoneNumber;

}

