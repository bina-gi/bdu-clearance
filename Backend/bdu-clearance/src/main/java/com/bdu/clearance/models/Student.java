package com.bdu.clearance.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String institute;
    private String faculty;
    private String advisor;
    private String year;
    private String isGraduated;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<Clearance> clearances;

}