package com.bdu.clearance.services;

import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalRequestDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;

import java.util.List;

public interface ClearanceApprovalService {

    void createClearanceApproval(ClearanceApprovalRequestDto requestDto);

    ClearanceApprovalResponseDto getClearanceApprovalById(Long id);

    List<ClearanceApprovalResponseDto> getAllClearanceApprovals();

    List<ClearanceApprovalResponseDto> getClearanceApprovalsByClearanceId(Long clearanceId);

    List<ClearanceApprovalResponseDto> getPendingByOrganizationalUnit(Long orgId);

    List<ClearanceApprovalResponseDto> getPendingForUser(String currentUserId);

    void updateClearanceApproval(Long id, ClearanceApprovalRequestDto requestDto);

    void deleteClearanceApproval(Long id);
}
