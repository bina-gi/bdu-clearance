package com.bdu.clearance.mappers;

import com.bdu.clearance.dto.role.RoleRequestDto;
import com.bdu.clearance.dto.role.RoleResponseDto;
import com.bdu.clearance.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDto toResponse(Role role);

    List<RoleResponseDto> toResponse(List<Role> roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toEntity(RoleRequestDto roleRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    void updateEntityFromDto(RoleRequestDto roleRequestDto, @MappingTarget Role role);
}
