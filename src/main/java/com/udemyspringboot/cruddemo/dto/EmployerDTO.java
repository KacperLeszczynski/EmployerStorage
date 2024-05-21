package com.udemyspringboot.cruddemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployerDTO {
    private int id;

    private String username;

    private String password;

    private String organizationName;
}
