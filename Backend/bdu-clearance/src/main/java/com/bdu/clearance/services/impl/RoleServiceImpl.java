package com.bdu.clearance.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bdu.clearance.dto.role.RoleRequestDto;
import com.bdu.clearance.dto.role.RoleResponseDto;
import com.bdu.clearance.enums.UserRole;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.exceptions.ResourceNotFoundException;
import com.bdu.clearance.mappers.RoleMapper;
import com.bdu.clearance.models.Role;
import com.bdu.clearance.repositories.RoleRepository;
import com.bdu.clearance.services.RoleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public void createRole(RoleRequestDto roleRequestDto) {
        // Check if role already exists
        if (roleRepository.existsByRoleName(roleRequestDto.getRoleName())) {
            throw new APIException("Role already exists with name: " + roleRequestDto.getRoleName());
        }

        Role role = roleMapper.toEntity(roleRequestDto);
        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponseDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roleMapper.toResponse(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponseDto getRoleByName(UserRole roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", roleName.toString()));

        return roleMapper.toResponse(role);
    }

    @Override
    @Transactional
    public void updateRole(Long id, RoleRequestDto roleRequestDto) {
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (!existingRole.getRoleName().equals(roleRequestDto.getRoleName()) &&
                roleRepository.existsByRoleName(roleRequestDto.getRoleName())) {
            throw new APIException("Role already exists with name: " + roleRequestDto.getRoleName());
        }

        roleMapper.updateEntityFromDto(roleRequestDto, existingRole);
        roleRepository.save(existingRole);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (role.getUsers() != null && !role.getUsers().isEmpty()) {
            throw new APIException("Cannot delete role. It is currently assigned to " +
                    role.getUsers().size() + " user(s)");
        }

        roleRepository.delete(role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRoleName(UserRole roleName) {
        return roleRepository.existsByRoleName(roleName);
    }
}