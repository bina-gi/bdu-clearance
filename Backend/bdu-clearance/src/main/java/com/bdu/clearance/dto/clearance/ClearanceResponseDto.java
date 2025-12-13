package com.bdu.clearance.dto.clearance;

import com.bdu.clearance.enums.ClearanceStatus;

public class ClearanceResponseDto {

    private Long id;

    private Long studentId;
    private int academicYear;
    private int yearOfStudy;
    private int semester;
    private String clearanceReason;

    private ClearanceStatus library;
    private String libraryStaffId;

    private ClearanceStatus cafeteria;
    private String cafeteriaStaffId;

    private ClearanceStatus dormitory;
    private String dormitoryStaffId;

    private ClearanceStatus registrar;
    private String registrarStaffId;

    private ClearanceStatus store;
    private String storeStaffId;

    private ClearanceStatus advisor;
    private String advisorStaffId;

    private ClearanceStatus faculty;
    private String facultyStaffId;

    private ClearanceStatus finance;
    private String financeStaffId;

    private Boolean isCleared;
}
