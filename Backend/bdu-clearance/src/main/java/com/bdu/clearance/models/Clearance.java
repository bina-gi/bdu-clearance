package com.bdu.clearance.models;

import com.bdu.clearance.enums.ClearanceStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Clearance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  Clearance Metadata 
    private int academicYear;
    private int yearOfStudy;
    private int semester;
    private String reason;


    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    //  Library 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus library = ClearanceStatus.PENDING;
    private String libraryStaffId;

    //  Cafeteria 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus cafeteria = ClearanceStatus.PENDING;
    private String cafeteriaStaffId;

    // Dormitory 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus dormitory = ClearanceStatus.PENDING;
    private String dormitoryStaffId;

    // Registrar 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus registrar = ClearanceStatus.PENDING;
    private String registrarStaffId;

    // Store 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus store = ClearanceStatus.PENDING;
    private String storeStaffId;

    //  Advisor 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus advisor = ClearanceStatus.PENDING;
    private String advisorStaffId;

    // Faculty 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus faculty = ClearanceStatus.PENDING;
    private String facultyStaffId;

    // Finance 
    @Enumerated(EnumType.STRING)
    private ClearanceStatus finance = ClearanceStatus.PENDING;
    private String financeStaffId;

    // Final result of the clearance record
    private Boolean isCleared;

    // Relation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_fk")
    private Student student;
}
