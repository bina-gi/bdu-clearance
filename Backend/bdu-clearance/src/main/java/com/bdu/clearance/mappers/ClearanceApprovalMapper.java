package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalRequestDto;
import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;
import com.bdu.clearance.models.ClearanceApproval;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClearanceApprovalMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clearance", ignore = true)
    @Mapping(target = "organizationalUnit", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "approvalOrder", ignore = true)
    @Mapping(target = "isRequired", ignore = true)
    @Mapping(target = "approvalDate", ignore = true)
    ClearanceApproval toEntity(ClearanceApprovalRequestDto dto);

    @Mapping(source = "organizationalUnit.id", target = "organizationalUnitId")
    @Mapping(source = "organizationalUnit.organizationName", target = "organizationalUnitName")
    @Mapping(source = "approvedBy.id", target = "approvedByUserId")
    @Mapping(target = "approvedByName", expression = "java(approval.getApprovedBy() != null ? approval.getApprovedBy().getFirstName() + \" \" + approval.getApprovedBy().getMiddleName() + \" \" + approval.getApprovedBy().getLastName() : null)")
    @Mapping(source = "approvalOrder", target = "approvalOrder")
    @Mapping(source = "isRequired", target = "isRequired")
    @Mapping(target = "canProcess", ignore = true) // Computed at service level
    ClearanceApprovalResponseDto toResponse(ClearanceApproval approval);

    List<ClearanceApprovalResponseDto> toResponse(List<ClearanceApproval> approvals);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "clearance", ignore = true)
    @Mapping(target = "organizationalUnit", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "approvalOrder", ignore = true)
    @Mapping(target = "isRequired", ignore = true)
    @Mapping(target = "approvalDate", ignore = true)
    void updateEntityFromDto(ClearanceApprovalRequestDto dto, @MappingTarget ClearanceApproval entity);
}
