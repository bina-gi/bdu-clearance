package com.bdu.clearance.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitRequestDto;
import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitResponseDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.OrganizationalUnitMapper;
import com.bdu.clearance.models.OrganizationalUnit;
import com.bdu.clearance.models.OrganizationalUnitType;
import com.bdu.clearance.repositories.OrganizationalUnitRepository;
import com.bdu.clearance.repositories.OrganizationalUnitTypeRepository;
import com.bdu.clearance.services.OrganizationalUnitService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

        private final OrganizationalUnitRepository organizationalUnitRepository;
        private final OrganizationalUnitTypeRepository organizationalUnitTypeRepository;
        private final OrganizationalUnitMapper organizationalUnitMapper;

        @Override
        @Transactional
        public void createOrganizationalUnit(OrganizationalUnitRequestDto requestDto) {
                OrganizationalUnitType unitType = organizationalUnitTypeRepository
                                .findById(requestDto.getOrganizationalUnitTypeId())
                                .orElseThrow(() -> new APIException(
                                                "Organizational Unit Type not found with id: "
                                                                + requestDto.getOrganizationalUnitTypeId()));

                OrganizationalUnit organizationalUnit = organizationalUnitMapper.toEntity(requestDto);

                if (requestDto.getParentOrganizationId() != null && !requestDto.getParentOrganizationId().isEmpty()) {
                        OrganizationalUnit parentUnit = organizationalUnitRepository
                                        .findByOrganizationId(requestDto.getParentOrganizationId())
                                        .orElseThrow(() -> new APIException(
                                                        "Parent Organizational Unit not found with id: "
                                                                        + requestDto.getParentOrganizationId()));
                        organizationalUnit.setParent(parentUnit);
                        organizationalUnit.setParentOrganizationId(requestDto.getParentOrganizationId());
                }

                organizationalUnit.setOrganizationalUnitType(unitType);

                organizationalUnitRepository.save(organizationalUnit);
        }

        @Override
        public List<OrganizationalUnitResponseDto> getAllOrganizationalUnits() {
                List<OrganizationalUnit> units = organizationalUnitRepository.findAll();

                return organizationalUnitMapper.toResponse(units);
        }

        @Override
        public OrganizationalUnitResponseDto getOrganizationalUnitById(Long id) {
                OrganizationalUnit unit = organizationalUnitRepository
                                .findById(id)
                                .orElseThrow(() -> new APIException("Organizational Unit not found with id: " + id));

                return organizationalUnitMapper.toResponse(unit);
        }

        @Override
        @Transactional
        public void updateOrganizationalUnit(OrganizationalUnitRequestDto requestDto, Long id) {
                OrganizationalUnit existingUnit = organizationalUnitRepository
                                .findById(id)
                                .orElseThrow(() -> new APIException("Organizational Unit not found with id: " + id));

                OrganizationalUnitType unitType = organizationalUnitTypeRepository
                                .findById(requestDto.getOrganizationalUnitTypeId())
                                .orElseThrow(() -> new APIException(
                                                "Organizational Unit Type not found with id: "
                                                                + requestDto.getOrganizationalUnitTypeId()));

                organizationalUnitMapper.updateEntityFromDto(
                                requestDto,
                                existingUnit);

                if (requestDto.getParentOrganizationId() != null && !requestDto.getParentOrganizationId().isEmpty()) {
                        OrganizationalUnit parentUnit = organizationalUnitRepository
                                        .findByOrganizationId(requestDto.getParentOrganizationId())
                                        .orElseThrow(() -> new APIException(
                                                        "Parent Organizational Unit not found with id: "
                                                                        + requestDto.getParentOrganizationId()));
                        existingUnit.setParent(parentUnit);
                        existingUnit.setParentOrganizationId(requestDto.getParentOrganizationId());
                }

                existingUnit.setOrganizationalUnitType(unitType);

                organizationalUnitRepository.save(existingUnit);
        }

        @Override
        @Transactional
        public void deleteOrganizationalUnit(Long id) {
                OrganizationalUnit unit = organizationalUnitRepository
                                .findById(id)
                                .orElseThrow(() -> new APIException("Organizational Unit not found with id: " + id));

                organizationalUnitRepository.delete(unit);
        }
}
