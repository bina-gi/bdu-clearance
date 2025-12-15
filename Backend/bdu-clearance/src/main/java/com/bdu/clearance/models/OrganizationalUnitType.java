package com.bdu.clearance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "organizational_unit_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalUnitType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String organizationType;
    // Relations
    @OneToMany(mappedBy = "organizational_unit_type", cascade = CascadeType.DETACH, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrganizationalUnit> organizationalUnit;
}
