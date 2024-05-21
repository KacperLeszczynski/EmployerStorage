package com.udemyspringboot.cruddemo.rest;

import com.udemyspringboot.cruddemo.dto.EmployerDTO;
import com.udemyspringboot.cruddemo.dto.LoginDTO;
import com.udemyspringboot.cruddemo.dto.LoginResponseDTO;
import com.udemyspringboot.cruddemo.dto.ResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.entity.Employer;
import com.udemyspringboot.cruddemo.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Employee Login", description = "Operations pertaining to login/register employer.")
@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController {
    private LoginService loginService;

    @Autowired
    public LoginRestController(LoginService myLoginService) {
        loginService = myLoginService;
    }

    @Operation(summary = "Login employer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login"),
            @ApiResponse(responseCode = "401", description = "Wrong credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginEmployer(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = loginService.loginEmployer(loginDTO);

        if (loginResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO());
        }

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    @Operation(summary = "Register employer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful register"),
            @ApiResponse(responseCode = "404", description = "Wrong credentials")
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerEmployer(@RequestBody EmployerDTO employerDTO) {
        boolean createdSuccessfully =  loginService.registerEmployer(employerDTO);

        if (!createdSuccessfully) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("404", "Error while registering user"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Employer registered successfully"));

    }
}
