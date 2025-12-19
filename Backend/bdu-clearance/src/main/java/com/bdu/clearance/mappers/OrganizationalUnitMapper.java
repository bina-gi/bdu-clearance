package com.bdu.clearance.mappers;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitRequestDto;
import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitResponseDto;
import com.bdu.clearance.models.OrganizationalUnit;

@Mapper(componentModel = "spring")
public interface OrganizationalUnitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizationalUnitType", ignore = true)
    @Mapping(target = "clearanceApprovals", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "properties", ignore = true)
    OrganizationalUnit toEntity(OrganizationalUnitRequestDto dto);

    @Mapping(source = "organizationalUnitType.id", target = "organizationalUnitTypeId")
    @Mapping(source = "organizationalUnitType.organizationType", target = "organizationalUnitTypeName")
    OrganizationalUnitResponseDto toResponse(OrganizationalUnit entity);

    List<OrganizationalUnitResponseDto> toResponse(List<OrganizationalUnit> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizationalUnitType", ignore = true)
    @Mapping(target = "clearanceApprovals", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "properties", ignore = true)
    void updateEntityFromDto(
            OrganizationalUnitRequestDto dto,
            @MappingTarget OrganizationalUnit existingEntity);
}
