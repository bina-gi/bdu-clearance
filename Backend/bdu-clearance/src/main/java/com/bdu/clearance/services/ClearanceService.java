package com.bdu.clearance.services;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;

import java.util.List;

public interface ClearanceService {
    List<ClearanceResponseDto> getAllClearances();
    List<ClearanceResponseDto> getClearanceByStudentId(String StudentId);

    void createClearance(StudentClearanceRequestDto clearance);

    void deleteClearance(Long clearanceId);



//update Sectors
//    String updateLibrary(ClearanceStatus status, Long clearanceId);
//    String updateFinance(ClearanceStatus status,Long clearanceId);
//    String updateRegistrar(ClearanceStatus status,Long clearanceId);
//    String updateCafeteria(ClearanceStatus status,Long clearanceId);
//    String updateDormitory(ClearanceStatus status,Long clearanceId);
//    String updateFacultyStore(ClearanceStatus status,Long clearanceId);
//    String updateAdvisor(ClearanceStatus status,Long clearanceId);
//    String updateFaculty(ClearanceStatus status,Long clearanceId);

}
