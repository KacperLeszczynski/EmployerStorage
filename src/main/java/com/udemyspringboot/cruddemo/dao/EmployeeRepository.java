package com.udemyspringboot.cruddemo.dao;

import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
