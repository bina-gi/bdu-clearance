package com.bdu.clearance.models;

import com.bdu.clearance.enums.Semester;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;


@Entity
@Table(name = "clearance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clearance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long academicYear;
    private Long yearOfStudy;

    @Enumerated(EnumType.STRING)
    private Semester semester;

    private String reason;

    //==RELATIONS==
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToMany(mappedBy = "clearance",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ClearanceApproval> approvals;


}
