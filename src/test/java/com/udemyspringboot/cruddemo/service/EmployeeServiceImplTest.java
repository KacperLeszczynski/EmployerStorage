package com.udemyspringboot.cruddemo.service;

import com.udemyspringboot.cruddemo.dao.EmployeeRepository;
import com.udemyspringboot.cruddemo.dao.EmployerRepository;
import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.entity.Employer;
import com.udemyspringboot.cruddemo.mapper.EmployeeMapper;
import com.udemyspringboot.cruddemo.utils.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        // Initialization logic if needed
    }

    @Test
    void testFindAll() {
        // Arrange
        String jwtToken = "sampleJwtToken";
        int employerId = 1;
        List<Employee> employees = List.of(mock(Employee.class), mock(Employee.class));
        when(employerRepository.findById(employerId)).thenReturn(Optional.of(new Employer(employerId, "", "", "", employees)));
        when(jwtUtil.extractData(jwtToken)).thenReturn(new Employer(employerId, "", "", ""));

        // Act
        List<EmployeeDTO> result = employeeService.findAll(jwtToken);

        // Assert
        verify(jwtUtil, times(1)).extractData(jwtToken);
        verify(employerRepository, times(1)).findById(employerId);
        assertEquals(2, result.size());
    }

    @Test
    void testFindById() {
        // Arrange
        int id = 1;
        String jwtToken = "sampleJwtToken";
        Employer employer = new Employer(1, "", "", "");

        Employee employee = new Employee(id, "", "", "", 0);
        employee.setEmployer(employer);

        when(jwtUtil.extractData(jwtToken)).thenReturn(employer);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Act
        EmployeeDTO result = employeeService.findById(id, jwtToken);

        // Assert
        verify(jwtUtil, times(1)).extractData(jwtToken);
        verify(employeeRepository, times(1)).findById(id);
        assertNotNull(result);
    }

    @Test
    void testSave() {
        // Arrange
        int id = 1;
        String jwtToken = "sampleJwtToken";
        Employer employer = new Employer(1, "", "", "");
        when(jwtUtil.extractData(jwtToken)).thenReturn(employer);
        when(employerRepository.findById(employer.getId())).thenReturn(Optional.of(employer));

        EmployeeDTO employeeDTO = new EmployeeDTO(id, "", "", "", 0);
        Employee savedEmployee = new Employee(0, "", "", "", 0);
        savedEmployee.setEmployer(employer);

        // Act
        Employee result = employeeService.save(employeeDTO, jwtToken);

        // Assert
        verify(jwtUtil, times(1)).extractData(jwtToken);
        verify(employerRepository, times(1)).findById(employer.getId());
//        verify(employeeRepository, times(1)).save(new Employee(0, "", "", "", 0));
//        assertNotNull(result);
    }

    @Test
    void testDeleteById() {
        // Arrange
        int id = 1;
        String jwtToken = "sampleJwtToken";
        Employee employee = new Employee(1, "", "", "", 0);
        Employer employer = new Employer(1, "", "", "", List.of(employee));
        employee.setEmployer(employer);
        when(jwtUtil.extractData(jwtToken)).thenReturn(employer);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));

        // Act
        employeeService.deleteById(id, jwtToken);

        // Assert
        verify(jwtUtil, times(1)).extractData(jwtToken);
        verify(employeeRepository, times(1)).findById(id);
        verify(employeeRepository, times(1)).deleteById(anyInt());
    }
}
