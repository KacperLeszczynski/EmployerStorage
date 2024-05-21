package com.udemyspringboot.cruddemo.service;

import com.udemyspringboot.cruddemo.dao.EmployerRepository;
import com.udemyspringboot.cruddemo.dto.LoginDTO;
import com.udemyspringboot.cruddemo.dto.LoginResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employer;
import com.udemyspringboot.cruddemo.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private EmployerRepository employerRepository;
    private PasswordEncoder passwordEncoder;
    private JWTUtil jwtUtil;

    @Autowired
    public LoginServiceImpl(EmployerRepository myEmployerRepository, PasswordEncoder myPasswordEncoder, JWTUtil myJwtUtil) {
        employerRepository = myEmployerRepository;
        passwordEncoder = myPasswordEncoder;
        jwtUtil = myJwtUtil;
    }

    @Override
    public LoginResponseDTO loginEmployer(LoginDTO loginDTO) {
        Optional<Employer> optionalEmployer = employerRepository.findByUsername(loginDTO.getUsername());

        if (optionalEmployer.isEmpty()) {
            return null;
        }

        Employer employer = optionalEmployer.get();;

        if (passwordEncoder.matches(loginDTO.getPassword(), employer.getPassword())) {
            return null;
        }

        String jwtToken = jwtUtil.generateToken(employer);
        return new LoginResponseDTO(jwtToken);
    }

    @Override
    public boolean registerEmployer(Employer employer) {
        try {
            employer.setPassword(passwordEncoder.encode(employer.getPassword()));
            employerRepository.save(employer);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
