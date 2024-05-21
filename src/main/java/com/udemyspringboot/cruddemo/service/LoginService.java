package com.udemyspringboot.cruddemo.service;

import com.udemyspringboot.cruddemo.dto.EmployerDTO;
import com.udemyspringboot.cruddemo.dto.LoginDTO;
import com.udemyspringboot.cruddemo.dto.LoginResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employee;
import com.udemyspringboot.cruddemo.entity.Employer;

public interface LoginService {
    LoginResponseDTO loginEmployer(LoginDTO loginDTO);
    boolean registerEmployer(EmployerDTO employer);
}
