package com.udemyspringboot.cruddemo.rest;

import com.udemyspringboot.cruddemo.dto.LoginDTO;
import com.udemyspringboot.cruddemo.dto.LoginResponseDTO;
import com.udemyspringboot.cruddemo.dto.ResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.entity.Employer;
import com.udemyspringboot.cruddemo.service.LoginService;
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

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class LoginRestController {
    private LoginService loginService;

    @Autowired
    public LoginRestController(LoginService myLoginService) {
        loginService = myLoginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginEmployer(@RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = loginService.loginEmployer(loginDTO);

        if (loginResponseDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO());
        }

        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerEmployer(@RequestBody Employer employer) {
        boolean createdSuccessfully =  loginService.registerEmployer(employer);

        if (!createdSuccessfully) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDTO("404", "Error while registering user"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO("201", "Employer registered successfully"));

    }
}
