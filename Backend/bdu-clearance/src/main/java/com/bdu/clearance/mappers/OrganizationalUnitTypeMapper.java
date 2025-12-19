package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeRequestDto;
import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeResponseDto;
import com.bdu.clearance.models.OrganizationalUnitType;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationalUnitTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizationalUnits", ignore = true)
    OrganizationalUnitType toEntity(OrganizationalUnitTypeRequestDto dto);

    OrganizationalUnitTypeResponseDto toResponse(OrganizationalUnitType entity);

    List<OrganizationalUnitTypeResponseDto> toResponse(List<OrganizationalUnitType> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizationalUnits", ignore = true)
    void updateEntityFromDto(OrganizationalUnitTypeRequestDto dto, @MappingTarget OrganizationalUnitType entity);
}
