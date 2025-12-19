package com.bdu.clearance.services;

import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;

import java.util.List;

public interface ClearanceService {
    List<ClearanceResponseDto> getAllClearances();

    List<ClearanceResponseDto> getClearanceByStudentId(String StudentId);

    void createClearance(StudentClearanceRequestDto clearance);

    void updateClearance(Long id, StudentClearanceRequestDto clearance);

    void deleteClearance(Long clearanceId);

}
