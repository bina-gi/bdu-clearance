package com.bdu.clearance.services;

import java.util.List;

import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitRequestDto;
import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitResponseDto;

public interface OrganizationalUnitService {
        //==Create==
    void createOrganizationalUnit(OrganizationalUnitRequestDto requestDto);
        //==Read==
    OrganizationalUnitResponseDto getOrganizationalUnitById(Long id);
    List<OrganizationalUnitResponseDto> getAllOrganizationalUnits();
        //==Update==
    void updateOrganizationalUnit(OrganizationalUnitRequestDto requestDto,Long id);
        //==Delete==
    void deleteOrganizationalUnit(Long id);
}
