package com.udemyspringboot.cruddemo.mapper;

import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.dto.EmployerDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.entity.Employer;

public class EmployerMapper {
    public static EmployerDTO mapToEmployerDto(Employer employer, EmployerDTO employerDTO) {
        employerDTO.setId(employer.getId());
        employerDTO.setUsername(employer.getUsername());
        employerDTO.setPassword(employer.getPassword());
        employerDTO.setOrganizationName(employer.getOrganizationName());
        return employerDTO;
    }

    public static Employer mapToEmployer(EmployerDTO employerDTO, Employer employer) {
        employer.setId(employerDTO.getId());
        employer.setUsername(employerDTO.getUsername());
        employer.setPassword(employerDTO.getPassword());
        employer.setOrganizationName(employerDTO.getOrganizationName());
        return employer;
    }
}
