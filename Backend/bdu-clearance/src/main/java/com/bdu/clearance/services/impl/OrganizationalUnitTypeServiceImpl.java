package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeRequestDto;
import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeResponseDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.OrganizationalUnitTypeMapper;
import com.bdu.clearance.models.OrganizationalUnitType;
import com.bdu.clearance.repositories.OrganizationalUnitTypeRepository;
import com.bdu.clearance.services.OrganizationalUnitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationalUnitTypeServiceImpl implements OrganizationalUnitTypeService {

    private final OrganizationalUnitTypeRepository repository;
    private final OrganizationalUnitTypeMapper mapper;

    @Override
    public void createOrganizationalUnitType(OrganizationalUnitTypeRequestDto requestDto) {
        OrganizationalUnitType type = mapper.toEntity(requestDto);
        repository.save(type);
    }

    @Override
    public OrganizationalUnitTypeResponseDto getOrganizationalUnitTypeById(Long id) {
        OrganizationalUnitType type = repository.findById(id)
                .orElseThrow(() -> new APIException("Organizational Unit Type not found with id: " + id));
        return mapper.toResponse(type);
    }

    @Override
    public List<OrganizationalUnitTypeResponseDto> getAllOrganizationalUnitTypes() {
        return mapper.toResponse(repository.findAll());
    }

    @Override
    public void updateOrganizationalUnitType(Long id, OrganizationalUnitTypeRequestDto requestDto) {
        OrganizationalUnitType existingType = repository.findById(id)
                .orElseThrow(() -> new APIException("Organizational Unit Type not found with id: " + id));

        mapper.updateEntityFromDto(requestDto, existingType);
        repository.save(existingType);
    }

    @Override
    public void deleteOrganizationalUnitType(Long id) {
        if (!repository.existsById(id)) {
            throw new APIException("Organizational Unit Type not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
