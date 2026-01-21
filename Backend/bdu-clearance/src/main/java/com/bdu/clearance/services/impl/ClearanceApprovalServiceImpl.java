package com.bdu.clearance.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalRequestDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.ClearanceApprovalMapper;
import com.bdu.clearance.models.Clearance;
import com.bdu.clearance.models.ClearanceApproval;
import com.bdu.clearance.models.OrganizationalUnit;
import com.bdu.clearance.repositories.ClearanceApprovalRepository;
import com.bdu.clearance.repositories.ClearanceRepository;
import com.bdu.clearance.repositories.OrganizationalUnitRepository;
import com.bdu.clearance.services.ClearanceApprovalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClearanceApprovalServiceImpl implements ClearanceApprovalService {

    private final ClearanceApprovalRepository clearanceApprovalRepository;
    private final ClearanceApprovalMapper clearanceApprovalMapper;
    private final ClearanceRepository clearanceRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;

    @Override
    public void createClearanceApproval(ClearanceApprovalRequestDto requestDto) {
        ClearanceApproval approval = clearanceApprovalMapper.toEntity(requestDto);

        // Fetch relations
        Clearance clearance = clearanceRepository.findById(requestDto.getClearanceId())
                .orElseThrow(() -> new APIException("Clearance", requestDto.getClearanceId()));
        approval.setClearance(clearance);

        OrganizationalUnit orgUnit = organizationalUnitRepository.findById(requestDto.getOrganizationalUnitId())
                .orElseThrow(() -> new APIException(
                "Organizational Unit", requestDto.getOrganizationalUnitId()));
        approval.setOrganizationalUnit(orgUnit);

        clearanceApprovalRepository.save(approval);
    }

    @Override
    public ClearanceApprovalResponseDto getClearanceApprovalById(Long id) {
        ClearanceApproval approval = clearanceApprovalRepository.findById(id)
                .orElseThrow(() -> new APIException("Clearance Approval", id));
        return clearanceApprovalMapper.toResponse(approval);
    }

    @Override
    public List<ClearanceApprovalResponseDto> getAllClearanceApprovals() {
        return clearanceApprovalMapper.toResponse(clearanceApprovalRepository.findAll());
    }

    @Override
    public List<ClearanceApprovalResponseDto> getClearanceApprovalsByClearanceId(Long clearanceId) {
        List<ClearanceApproval> approvals = clearanceApprovalRepository
                .findByClearanceIdOrderByApprovalOrderAsc(clearanceId);

        List<ClearanceApprovalResponseDto> responseDtos = clearanceApprovalMapper.toResponse(approvals);

        // Compute canProcess for each approval
        for (ClearanceApprovalResponseDto dto : responseDtos) {
            dto.setCanProcess(canApprovalBeProcessed(approvals, dto.getApprovalOrder()));
        }

        return responseDtos;
    }

    private boolean canApprovalBeProcessed(List<ClearanceApproval> allApprovals, Integer currentOrder) {
        if (currentOrder == null || currentOrder <= 1) {
            return true;
        }

        return allApprovals.stream()
                .filter(a -> a.getApprovalOrder() != null && a.getApprovalOrder() < currentOrder)
                .filter(a -> Boolean.TRUE.equals(a.getIsRequired()))
                .allMatch(a -> a.getStatus() == com.bdu.clearance.enums.ApprovalStatus.APPROVED);
    }

    @Override
    public void updateClearanceApproval(Long id, ClearanceApprovalRequestDto requestDto) {
        ClearanceApproval existingApproval = clearanceApprovalRepository.findById(id)
                .orElseThrow(() -> new APIException("Clearance Approval", id));

        clearanceApprovalMapper.updateEntityFromDto(requestDto, existingApproval);

        if (requestDto.getOrganizationalUnitId() != null) {
            OrganizationalUnit orgUnit = organizationalUnitRepository.findById(requestDto.getOrganizationalUnitId())
                    .orElseThrow(() -> new APIException(
                    "Organizational Unit", requestDto.getOrganizationalUnitId()));
            existingApproval.setOrganizationalUnit(orgUnit);
        }

        if (existingApproval.getStatus() != com.bdu.clearance.enums.ApprovalStatus.PENDING) {
            existingApproval.setApprovalDate(java.time.LocalDateTime.now());
        }

        clearanceApprovalRepository.save(existingApproval);
    }

    @Override
    public List<ClearanceApprovalResponseDto> getPendingByOrganizationalUnit(Long orgId) {
        return clearanceApprovalMapper.toResponse(
                clearanceApprovalRepository.findPendingByOrganizationalUnit(orgId));
    }

    @Override
    public void deleteClearanceApproval(Long id) {
        if (!clearanceApprovalRepository.existsById(id)) {
            throw new APIException("Clearance Approval", id);
        }
        clearanceApprovalRepository.deleteById(id);
    }
}
