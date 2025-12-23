package com.bdu.clearance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "organizationalUnitType", fetch = FetchType.LAZY)
    private List<OrganizationalUnit> organizationalUnits;
}
