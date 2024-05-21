package com.udemyspringboot.cruddemo.service;

import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> findAll(String JWTToken);

    EmployeeDTO findById(int id, String JWTToken);

    Employee save(EmployeeDTO employeeDTO, String JWTToken);

    void deleteById(int id, String JWTToken);
}
