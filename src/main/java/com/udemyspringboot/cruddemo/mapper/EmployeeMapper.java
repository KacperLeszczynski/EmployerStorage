package com.udemyspringboot.cruddemo.mapper;

import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDTO mapToEmployeeDto(Employee employee, EmployeeDTO employeeDTO) {
        employeeDTO.setId(employee.getId());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setAge(employee.getAge());
        return employeeDTO;
    }

    public static Employee mapToEmployee(EmployeeDTO employeeDTO, Employee employee) {
        employee.setId(employeeDTO.getId());
        employee.setEmail(employeeDTO.getEmail());
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setAge(employeeDTO.getAge());
        return employee;
    }
}
