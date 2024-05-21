package com.udemyspringboot.cruddemo.service;

import com.udemyspringboot.cruddemo.dao.EmployeeRepository;
import com.udemyspringboot.cruddemo.dao.EmployerRepository;
import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.entity.Employer;
import com.udemyspringboot.cruddemo.mapper.EmployeeMapper;
import com.udemyspringboot.cruddemo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;
    private EmployerRepository employerRepository;
    private JWTUtil jwtUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployerRepository employerRepository, JWTUtil jwtUtil) {
        this.employeeRepository = employeeRepository;
        this.employerRepository = employerRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public List<EmployeeDTO> findAll(String JWTToken) {

        Integer employerId = jwtUtil.extractData(JWTToken).getId();
        return employerRepository.findById(employerId)
                .get().getEmployees()
                .stream().map(employee -> EmployeeMapper.mapToEmployeeDto(employee, new EmployeeDTO())).toList();


    }

    @Override
    public EmployeeDTO findById(int id, String JWTToken) {
        Employer employer = jwtUtil.extractData(JWTToken);
        if (employer == null) {
            return null;
        }


        Optional<Employee> optionalResult = employeeRepository.findById(id);

        if (optionalResult.isPresent()) {
            Employee result = optionalResult.get();

            if (result.getEmployer().getId() != employer.getId()) {
                throw new RuntimeException("Employer doesnt have this Employee!");
            }

            return EmployeeMapper.mapToEmployeeDto(result, new EmployeeDTO());
        }

        return null;
    }

    @Override
    public Employee save(EmployeeDTO employeeDTO, String JWTToken) {
        Integer employerId = jwtUtil.extractData(JWTToken).getId();
        Employer employer = employerRepository.findById(employerId).orElseThrow(RuntimeException::new);

        Employee employee = EmployeeMapper.mapToEmployee(employeeDTO, new Employee());
        employee.setEmployer(employer);
        employee.setId(0);

        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(int id, String JWTToken) {
        Integer employerId = jwtUtil.extractData(JWTToken).getId();
        Optional<Employee> optionalResult = employeeRepository.findById(id);

        if (optionalResult.isEmpty()) {
            return;
        }


        Employee employee = optionalResult.get();
        if (employee.getEmployer().getId() != employerId) {
            throw new RuntimeException("Employer doesnt have this Employee!");
        }

        employeeRepository.deleteById(employee.getId());
    }
}
