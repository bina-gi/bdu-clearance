package com.bdu.clearance.services;

import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeRequestDto;
import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeResponseDto;

import java.util.List;

public interface OrganizationalUnitTypeService {
    void createOrganizationalUnitType(OrganizationalUnitTypeRequestDto requestDto);

    OrganizationalUnitTypeResponseDto getOrganizationalUnitTypeById(Long id);

    List<OrganizationalUnitTypeResponseDto> getAllOrganizationalUnitTypes();

    void updateOrganizationalUnitType(Long id, OrganizationalUnitTypeRequestDto requestDto);

    void deleteOrganizationalUnitType(Long id);
}
