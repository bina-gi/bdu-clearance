package com.bdu.clearance.services;

import java.util.List;

import com.bdu.clearance.dto.role.RoleRequestDto;
import com.bdu.clearance.dto.role.RoleResponseDto;
import com.bdu.clearance.enums.UserRole;

public interface RoleService {
    void createRole(RoleRequestDto roleRequestDto);

    List<RoleResponseDto> getAllRoles();

    RoleResponseDto getRoleById(Long id);

    RoleResponseDto getRoleByName(UserRole roleName);

    void updateRole(Long id, RoleRequestDto roleRequestDto);

    void deleteRole(Long id);

    boolean existsByRoleName(UserRole roleName);
}
