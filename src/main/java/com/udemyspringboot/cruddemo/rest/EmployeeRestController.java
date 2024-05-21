package com.udemyspringboot.cruddemo.rest;

import com.udemyspringboot.cruddemo.dto.EmployeeDTO;
import com.udemyspringboot.cruddemo.dto.ResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.service.EmployeeService;
import com.udemyspringboot.cruddemo.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Employee Management", description = "Operations pertaining to employee management.")
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

    @Operation(summary = "Retrieve all employees",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer JWT token for authentication", required = true)
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of employees"),
            @ApiResponse(responseCode = "500", description = "Some error")
    })
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@RequestHeader(value = "Authorization") String authorizationHeader) {
        String JWTToken = jwtUtil.processAuthorization(authorizationHeader);

        return ResponseEntity.status(HttpStatus.OK).body(employeeService.findAll(JWTToken));
    }

    @Operation(summary = "Retrieve one employee",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer JWT token for authentication", required = true)
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of employee"),
            @ApiResponse(responseCode = "500", description = "Some error")
    })
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
    @Operation(summary = "Creation of employee",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer JWT token for authentication", required = true)
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful saved employee"),
            @ApiResponse(responseCode = "500", description = "Some error")
    })
    public ResponseEntity<ResponseDTO> addEmployee(@RequestBody EmployeeDTO theEmployee,
                                                   @RequestHeader(value = "Authorization") String authorizationHeader) {
        String JWTToken = jwtUtil.processAuthorization(authorizationHeader);

        employeeService.save(theEmployee, JWTToken);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Account added successfully"));
    }

    @Operation(
            summary = "Deletion of employee",
            parameters = {
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Bearer JWT token for authentication", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful deleted employee"),
            @ApiResponse(responseCode = "500", description = "Some error")
    })
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


























