package com.bdu.clearance.controllers.role;

import com.bdu.clearance.dto.role.RoleRequestDto;
import com.bdu.clearance.dto.role.RoleResponseDto;
import com.bdu.clearance.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/public/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    // === CREATE ===
    @PostMapping
    public ResponseEntity<Void> createRole(@Valid @RequestBody RoleRequestDto roleDto) {
        roleService.createRole(roleDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // === READ ===
    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    // === UPDATE ===
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRole(@Valid @RequestBody RoleRequestDto roleDto, @PathVariable Long id) {
        roleService.updateRole(id, roleDto);
        return ResponseEntity.ok().build();
    }

    // === DELETE ===
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
