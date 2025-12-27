package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.clearance.ClearanceRequestDto;
import com.bdu.clearance.dto.clearance.ClearanceResponseDto;
import com.bdu.clearance.dto.clearance.StudentClearanceRequestDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;
import com.bdu.clearance.models.ClearanceApproval;
import com.bdu.clearance.models.Clearance;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClearanceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approvals", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestDate", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    @Mapping(source = "clearanceReason", target = "reason")
    Clearance toEntity(StudentClearanceRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approvals", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestDate", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    Clearance toEntity(ClearanceRequestDto dto);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(target = "studentName", expression = "java(clearance.getStudent().getUser().getFirstName() + \" \" + clearance.getStudent().getUser().getMiddleName() + \" \" + clearance.getStudent().getUser().getLastName())")
    @Mapping(target = "totalApprovals", expression = "java(clearance.getApprovals() != null ? clearance.getApprovals().size() : 0)")
    @Mapping(target = "approvedCount", expression = "java(clearance.getApprovals() != null ? (int) clearance.getApprovals().stream().filter(a -> a.getStatus() == com.bdu.clearance.enums.ApprovalStatus.APPROVED).count() : 0)")
    @Mapping(target = "pendingCount", expression = "java(clearance.getApprovals() != null ? (int) clearance.getApprovals().stream().filter(a -> a.getStatus() == com.bdu.clearance.enums.ApprovalStatus.PENDING).count() : 0)")
    ClearanceResponseDto toResponse(Clearance clearance);

    // Helper for Approvals
    @Mapping(source = "organizationalUnit.id", target = "organizationalUnitId")
    @Mapping(source = "organizationalUnit.organizationName", target = "organizationalUnitName")
    @Mapping(source = "approvedBy.id", target = "approvedByUserId")
    @Mapping(target = "approvedByName", expression = "java(approval.getApprovedBy() != null ? approval.getApprovedBy().getFirstName() + \" \" + approval.getApprovedBy().getMiddleName() + \" \" + approval.getApprovedBy().getLastName() : null)")
    ClearanceApprovalResponseDto toResponse(ClearanceApproval approval);

    List<ClearanceResponseDto> toResponse(List<Clearance> students);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approvals", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestDate", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    @Mapping(source = "clearanceReason", target = "reason")
    void updateEntityFromDto(StudentClearanceRequestDto clearanceDto, @MappingTarget Clearance existingClearance);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approvals", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "requestDate", ignore = true)
    @Mapping(target = "completionDate", ignore = true)
    void updateEntityFromDto(ClearanceRequestDto clearanceDto, @MappingTarget Clearance existingClearance);
}
