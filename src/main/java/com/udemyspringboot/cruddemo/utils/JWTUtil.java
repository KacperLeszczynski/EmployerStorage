package com.udemyspringboot.cruddemo.utils;

import com.udemyspringboot.cruddemo.entity.Employer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${spring.jwt.secret}")
    private String SECRET;
    private final long EXPIRATION_TIME = 864_000_000; // 10 days
    public String generateToken(Employer employer) {
        return Jwts.builder()
                .setId(Integer.toString(employer.getId())) // Set the ID claim
                .setSubject(employer.getUsername()) // Set the subject claim
                .claim("organization_name", employer.getOrganizationName()) // Set additional claims
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set the expiration time
                .signWith(SignatureAlgorithm.HS512, SECRET) // Sign the token with HS512 algorithm
                .compact(); // Compact the token into a compact string representation
    }
    public Employer extractData(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            // Corrected claim names based on the generateToken method
            int id = Integer.parseInt(claims.getId());
            String sub = (String) claims.get("sub"); // Corrected from "subject"
            String organizationName = (String) claims.get("organization_name");

            Employer employer = new Employer();
            employer.setId(id);
            employer.setUsername(sub);
            employer.setOrganizationName(organizationName);
            return employer;

        } catch (Exception e) {
            // Log the error or throw a custom exception
            System.err.println("Failed to extract data from token: " + e.getMessage());
            // Consider returning an empty map or throwing a custom exception
            return null;
        }
    }

    public String processAuthorization(String authorization) {
        return authorization.replaceFirst("Bearer ", "");
    }
}