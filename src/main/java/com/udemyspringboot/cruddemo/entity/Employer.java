package com.udemyspringboot.cruddemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employer")
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="organization_name")
    private String organizationName;

    @OneToMany(mappedBy = "employer",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL)
    private List<Employee> employees;

    public Employer(String username, String password, String organizationName) {
        this.username = username;
        this.password = password;
        this.organizationName = organizationName;
    }

    public Employer(Integer id, String username, String password, String organizationName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.organizationName = organizationName;
    }
}
