package com.bdu.clearance.controllers.property;

import com.bdu.clearance.dto.property.PropertyRequestDto;
import com.bdu.clearance.dto.property.PropertyResponseDto;
import com.bdu.clearance.services.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    // === CREATE ===
    @PostMapping("create")
    public ResponseEntity<Void> createProperty(@Valid @RequestBody PropertyRequestDto requestDto) {
        propertyService.createProperty(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<PropertyResponseDto>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponseDto> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<PropertyResponseDto>> getPropertiesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(propertyService.getPropertiesByStudentId(studentId));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProperty(@PathVariable Long id,
            @Valid @RequestBody PropertyRequestDto requestDto) {
        propertyService.updateProperty(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id) {
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
