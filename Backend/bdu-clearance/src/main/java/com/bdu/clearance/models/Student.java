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
public class Student extends Users {

    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    private Department department;

    private String advisor;
    private int year;


    //   SECTOR STATUSES
//    @Enumerated(EnumType.STRING)
//    private SectorStatus libraryStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus cafeteriaStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus dormitoryStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus registrarStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus storeStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus advisorStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus facultyStatus = SectorStatus.CLEAN;
//
//    @Enumerated(EnumType.STRING)
//    private SectorStatus financeStatus = SectorStatus.CLEAN;

    @Enumerated(EnumType.STRING)
    private StudentStatus studentStatus = StudentStatus.ACTIVE;

    private boolean isEligibleForClearance;


    // RELATIONS
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Clearance> clearances;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Properties> properties;
}
