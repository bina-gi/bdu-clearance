package com.bdu.clearance.services.impl;

import com.bdu.clearance.dto.property.PropertyRequestDto;
import com.bdu.clearance.dto.property.PropertyResponseDto;
import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.mappers.PropertyMapper;
import com.bdu.clearance.models.OrganizationalUnit;
import com.bdu.clearance.models.Property;
import com.bdu.clearance.models.Student;
import com.bdu.clearance.models.Users;
import com.bdu.clearance.repositories.OrganizationalUnitRepository;
import com.bdu.clearance.repositories.PropertyRepository;
import com.bdu.clearance.repositories.StudentRepository;
import com.bdu.clearance.repositories.UserRepository;
import com.bdu.clearance.services.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final StudentRepository studentRepository;
    private final OrganizationalUnitRepository organizationalUnitRepository;
    private final UserRepository userRepository;

    @Override
    public void createProperty(PropertyRequestDto requestDto) {
        Property property = propertyMapper.toEntity(requestDto);

        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new APIException("Student not found with id: " + requestDto.getStudentId()));
        property.setStudent(student);

        OrganizationalUnit orgUnit = organizationalUnitRepository.findById(requestDto.getOrganizationalUnitId())
                .orElseThrow(() -> new APIException(
                        "Organizational Unit not found with id: " + requestDto.getOrganizationalUnitId()));
        property.setOrganizationalUnit(orgUnit);

        Users issuedBy = userRepository.findById(requestDto.getIssuedByUserId())
                .orElseThrow(() -> new APIException(
                        "User (Issued By) not found with id: " + requestDto.getIssuedByUserId()));
        property.setIssuedBy(issuedBy);

        if (property.getBorrowDate() == null) {
            property.setBorrowDate(java.time.LocalDateTime.now());
        }

        propertyRepository.save(property);
    }

    @Override
    public PropertyResponseDto getPropertyById(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new APIException("Property not found with id: " + id));
        return propertyMapper.toResponse(property);
    }

    @Override
    public List<PropertyResponseDto> getAllProperties() {
        return propertyMapper.toResponse(propertyRepository.findAll());
    }

    @Override
    public List<PropertyResponseDto> getPropertiesByStudentId(Long studentId) {
        return propertyMapper.toResponse(propertyRepository.findByStudentId(studentId));
    }

    @Override
    public void updateProperty(Long id, PropertyRequestDto requestDto) {
        Property existingProperty = propertyRepository.findById(id)
                .orElseThrow(() -> new APIException("Property not found with id: " + id));

        propertyMapper.updateEntityFromDto(requestDto, existingProperty);

        // Update relations if IDs changed
        if (!existingProperty.getOrganizationalUnit().getId().equals(requestDto.getOrganizationalUnitId())) {
            OrganizationalUnit orgUnit = organizationalUnitRepository.findById(requestDto.getOrganizationalUnitId())
                    .orElseThrow(() -> new APIException(
                            "Organizational Unit not found with id: " + requestDto.getOrganizationalUnitId()));
            existingProperty.setOrganizationalUnit(orgUnit);
        }

        // Update IssuedBy if changed
        if (!existingProperty.getIssuedBy().getId().equals(requestDto.getIssuedByUserId())) {
            Users issuedBy = userRepository.findById(requestDto.getIssuedByUserId())
                    .orElseThrow(() -> new APIException(
                            "User (Issued By) not found with id: " + requestDto.getIssuedByUserId()));
            existingProperty.setIssuedBy(issuedBy);
        }

        propertyRepository.save(existingProperty);
    }

    @Override
    public List<PropertyResponseDto> getOutstandingByOrganizationalUnit(Long orgId) {
        return propertyMapper.toResponse(
                propertyRepository.findByBorrowStatusAndOrganizationalUnitId(
                        com.bdu.clearance.enums.BorrowStatus.BORROWED, orgId));
    }

    @Override
    public List<PropertyResponseDto> getOutstandingByStudentId(Long studentId) {
        return propertyMapper.toResponse(
                propertyRepository.findByStudentIdAndBorrowStatus(
                        studentId, com.bdu.clearance.enums.BorrowStatus.BORROWED));
    }

    @Override
    public void markAsReturned(Long propertyId, Long returnedToUserId, String remarks) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new APIException("Property not found with id: " + propertyId));

        if (property.getBorrowStatus() == com.bdu.clearance.enums.BorrowStatus.RETURNED) {
            throw new APIException("Property has already been returned");
        }

        Users returnedTo = userRepository.findById(returnedToUserId)
                .orElseThrow(() -> new APIException("User not found with id: " + returnedToUserId));

        property.setBorrowStatus(com.bdu.clearance.enums.BorrowStatus.RETURNED);
        property.setReturnDate(java.time.LocalDateTime.now());
        property.setReturnedTo(returnedTo);
        propertyRepository.save(property);
    }

    @Override
    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new APIException("Property not found with id: " + id);
        }
        propertyRepository.deleteById(id);
    }
}
