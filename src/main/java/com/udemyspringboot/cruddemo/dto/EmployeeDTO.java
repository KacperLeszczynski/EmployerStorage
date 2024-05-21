package com.udemyspringboot.cruddemo.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
