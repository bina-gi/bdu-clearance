package com.bdu.clearance.models;

import com.bdu.clearance.enums.*;
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

    @Column(unique = true, nullable = false)
    private String studentId;

    private String firstName;
    private String middleName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String advisor;
    private int year;


    //   SECTOR STATUSES
    @Enumerated(EnumType.STRING)
    private SectorStatus libraryStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus cafeteriaStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus dormitoryStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus registrarStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus storeStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus advisorStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus facultyStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private SectorStatus financeStatus = SectorStatus.FREE;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus = StudentStatus.ACTIVE;

    private String password;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Clearance> clearances;
}
