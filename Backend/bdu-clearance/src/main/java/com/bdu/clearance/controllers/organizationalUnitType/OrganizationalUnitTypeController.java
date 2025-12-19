package com.bdu.clearance.controllers.organizationalUnitType;

import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeRequestDto;
import com.bdu.clearance.dto.organizationalUnitType.OrganizationalUnitTypeResponseDto;
import com.bdu.clearance.services.OrganizationalUnitTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/organizational-unit-types")
@RequiredArgsConstructor
public class OrganizationalUnitTypeController {

    private final OrganizationalUnitTypeService service;

    // === CREATE ===
    @PostMapping("create")
    public ResponseEntity<Void> createOrganizationalUnitType(
            @Valid @RequestBody OrganizationalUnitTypeRequestDto requestDto) {
        service.createOrganizationalUnitType(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<OrganizationalUnitTypeResponseDto>> getAllOrganizationalUnitTypes() {
        return ResponseEntity.ok(service.getAllOrganizationalUnitTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationalUnitTypeResponseDto> getOrganizationalUnitTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrganizationalUnitTypeById(id));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganizationalUnitType(@PathVariable Long id,
            @Valid @RequestBody OrganizationalUnitTypeRequestDto requestDto) {
        service.updateOrganizationalUnitType(id, requestDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationalUnitType(@PathVariable Long id) {
        service.deleteOrganizationalUnitType(id);
        return ResponseEntity.noContent().build();
    }
}
