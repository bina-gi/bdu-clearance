package com.bdu.clearance.services.impl;

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
import com.bdu.clearance.repositories.UserRepository;
import com.bdu.clearance.services.ClearanceApprovalService;
import com.bdu.clearance.models.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClearanceApprovalServiceImpl implements ClearanceApprovalService {

    private final ClearanceApprovalRepository clearanceApprovalRepository;
    private final ClearanceApprovalMapper clearanceApprovalMapper;
    private final ClearanceRepository clearanceRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final UserRepository userRepository;

    @Override
    public void createClearanceApproval(ClearanceApprovalRequestDto requestDto) {
        ClearanceApproval approval = clearanceApprovalMapper.toEntity(requestDto);

        // Fetch relations
        Clearance clearance = clearanceRepository.findById(requestDto.getClearanceId())
                .orElseThrow(() -> new APIException("Clearance not found with id: " + requestDto.getClearanceId()));
        approval.setClearance(clearance);

        OrganizationalUnit orgUnit = organizationalUnitRepository.findById(requestDto.getOrganizationalUnitId())
                .orElseThrow(() -> new APIException(
                        "Organizational Unit not found with id: " + requestDto.getOrganizationalUnitId()));
        approval.setOrganizationalUnit(orgUnit);

        Users approvedBy = userRepository.findById(requestDto.getApprovedByUserId())
                .orElseThrow(() -> new APIException(
                        "User (Approved By) not found with id: " + requestDto.getApprovedByUserId()));
        approval.setApprovedBy(approvedBy);

        clearanceApprovalRepository.save(approval);
    }

    @Override
    public ClearanceApprovalResponseDto getClearanceApprovalById(Long id) {
        ClearanceApproval approval = clearanceApprovalRepository.findById(id)
                .orElseThrow(() -> new APIException("Clearance Approval not found with id: " + id));
        return clearanceApprovalMapper.toResponse(approval);
    }

    @Override
    public List<ClearanceApprovalResponseDto> getAllClearanceApprovals() {
        return clearanceApprovalMapper.toResponse(clearanceApprovalRepository.findAll());
    }

    @Override
    public List<ClearanceApprovalResponseDto> getClearanceApprovalsByClearanceId(Long clearanceId) {
        return clearanceApprovalMapper.toResponse(clearanceApprovalRepository.findByClearanceId(clearanceId));
    }

    @Override
    public void updateClearanceApproval(Long id, ClearanceApprovalRequestDto requestDto) {
        ClearanceApproval existingApproval = clearanceApprovalRepository.findById(id)
                .orElseThrow(() -> new APIException("Clearance Approval not found with id: " + id));

        clearanceApprovalMapper.updateEntityFromDto(requestDto, existingApproval);

        if (requestDto.getOrganizationalUnitId() != null) {
            OrganizationalUnit orgUnit = organizationalUnitRepository.findById(requestDto.getOrganizationalUnitId())
                    .orElseThrow(() -> new APIException(
                            "Organizational Unit not found with id: " + requestDto.getOrganizationalUnitId()));
            existingApproval.setOrganizationalUnit(orgUnit);
        }

        if (requestDto.getApprovedByUserId() != null) {
            Users approvedBy = userRepository.findById(requestDto.getApprovedByUserId())
                    .orElseThrow(() -> new APIException(
                            "User (Approved By) not found with id: " + requestDto.getApprovedByUserId()));
            existingApproval.setApprovedBy(approvedBy);
        }

        clearanceApprovalRepository.save(existingApproval);
    }

    @Override
    public void deleteClearanceApproval(Long id) {
        if (!clearanceApprovalRepository.existsById(id)) {
            throw new APIException("Clearance Approval not found with id: " + id);
        }
        clearanceApprovalRepository.deleteById(id);
    }
}
