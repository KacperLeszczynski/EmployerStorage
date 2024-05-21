package com.udemyspringboot.cruddemo.rest;

import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.dto.ResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.service.EmployeeService;
import com.udemyspringboot.cruddemo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    private EmployeeService employeeService;
    private JWTUtil jwtUtil;

    @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService, JWTUtil jwtUtil) {
        employeeService = theEmployeeService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String JWTToken = jwtUtil.processAuthorization(authorizationHeader);

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll(JWTToken));
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable int employeeId,
                                                @RequestHeader(value = "Authorization") String authorizationHeader) {

        String JWTToken = jwtUtil.processAuthorization(authorizationHeader);
        EmployeeDTO theEmployee = employeeService.findById(employeeId, JWTToken);

        if (theEmployee == null) {
            throw new RuntimeException("Employee id not found: " + employeeId);
        }

        return ResponseEntity.status(HttpStatus.OK).body(theEmployee);
    }

    @PostMapping("/employees")
    public ResponseEntity<ResponseDTO> addEmployee(@RequestBody EmployeeDTO theEmployee,
                                                   @RequestHeader(value = "Authorization") String authorizationHeader) {
        String JWTToken = jwtUtil.processAuthorization(authorizationHeader);

        employeeService.save(theEmployee, JWTToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Account added successfully"));
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<ResponseDTO> deleteEmployee(@PathVariable int employeeId,
                                                      @RequestHeader(value = "Authorization") String authorizationHeader) {

        String JWTToken = jwtUtil.processAuthorization(authorizationHeader);
        EmployeeDTO dbEmployee = employeeService.findById(employeeId, JWTToken);

        if (dbEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(employeeId, JWTToken);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("200", "Deleted employee id - " + employeeId));
    }
}


























