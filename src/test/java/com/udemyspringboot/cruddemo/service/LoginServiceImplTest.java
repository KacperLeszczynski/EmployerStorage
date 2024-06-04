package com.udemyspringboot.cruddemo.service;

import com.udemyspringboot.cruddemo.dao.EmployerRepository;
import com.udemyspringboot.cruddemo.dto.LoginDTO;
import com.udemyspringboot.cruddemo.dto.LoginResponseDTO;
import com.udemyspringboot.cruddemo.entity.Employer;
import com.udemyspringboot.cruddemo.utils.JWTUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {
    @Mock
    private EmployerRepository employerRepository;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;


    @InjectMocks
    private LoginServiceImpl loginService;

    @ParameterizedTest
    @MethodSource("testCreateEmployerExpectedResponse")
    void testCreateEmployer(String token, String username, String password) {
        // act
        Employer employer = new Employer();
        employer.setUsername("testUser");
        employer.setPassword("hashedPassword");
        when(employerRepository.findByUsername(anyString())).thenReturn(Optional.of(employer));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateToken(any())).thenReturn(token);
        LoginResponseDTO expectedResponse = new LoginResponseDTO(token);

        // arrange
        LoginResponseDTO response = loginService.loginEmployer(new LoginDTO(username, password));

        // assert
        Assertions.assertEquals(response.getJwtToken(), expectedResponse.getJwtToken());
    }


    static Stream<Object[]> testCreateEmployerExpectedResponse() {
        return Stream.of(
                new Object[]{"token", "testUser", "password"},
                new Object[]{null, "testUser", "another_password"}
        );
    }
}
