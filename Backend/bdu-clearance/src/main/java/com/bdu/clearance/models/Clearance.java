package com.bdu.clearance.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Clearance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean libraryStatus;
    private Boolean financeStatus;
    private Boolean registrarStatus;
    private Boolean cafteriaStatus;
    private Boolean dormitoryStatus;
    private Boolean facultyStoreStatus;
    private Boolean advisorStatus;
    private Boolean facultyStatus;
    private Boolean isCleared;

    @ManyToOne
    @JoinColumn(name = "student_fk")
    private Student student;
}