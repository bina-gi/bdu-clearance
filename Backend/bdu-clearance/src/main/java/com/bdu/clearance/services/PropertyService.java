package com.bdu.clearance.services;

import com.bdu.clearance.dto.property.PropertyRequestDto;
import com.bdu.clearance.dto.property.PropertyResponseDto;

import java.util.List;

public interface PropertyService {
    void createProperty(PropertyRequestDto requestDto);

    PropertyResponseDto getPropertyById(Long id);

    List<PropertyResponseDto> getAllProperties();

    List<PropertyResponseDto> getPropertiesByStudentId(Long studentId);

    void updateProperty(Long id, PropertyRequestDto requestDto);

    void deleteProperty(Long id);
}
