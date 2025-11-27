package com.bdu.clearance.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

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
    private String studentId;
    private Boolean libraryStatus;
    private Boolean financeStatus;
    private Boolean registrarStatus;
    private Boolean cafteriaStatus;
    private Boolean dormitoryStatus;
    private Boolean facultyStoreStatus;
    private Boolean advisorStatus;
    private Boolean facultyStatus;
    private Boolean isCleared;
}