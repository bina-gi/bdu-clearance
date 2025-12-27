package com.bdu.clearance.controllers.organizationalUnit;

import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitRequestDto;
import com.bdu.clearance.dto.organizationalUnit.OrganizationalUnitResponseDto;
import com.bdu.clearance.services.OrganizationalUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/organizational-units")
@RequiredArgsConstructor
public class OrganizationalUnitController {

    private final OrganizationalUnitService organizationalUnitService;

    // === CREATE ===
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createOrganizationalUnit(@Valid @RequestBody OrganizationalUnitRequestDto requestDto) {
        organizationalUnitService.createOrganizationalUnit(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrganizationalUnitResponseDto>> getAllOrganizationalUnits() {
        return ResponseEntity.ok(organizationalUnitService.getAllOrganizationalUnits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationalUnitResponseDto> getOrganizationalUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(organizationalUnitService.getOrganizationalUnitById(id));
    }

    // === UPDATE ===
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrganizationalUnit(@Valid @RequestBody OrganizationalUnitRequestDto requestDto,
            @PathVariable Long id) {
        organizationalUnitService.updateOrganizationalUnit(requestDto, id);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationalUnit(@PathVariable Long id) {
        organizationalUnitService.deleteOrganizationalUnit(id);
        return ResponseEntity.noContent().build();
    }
}
