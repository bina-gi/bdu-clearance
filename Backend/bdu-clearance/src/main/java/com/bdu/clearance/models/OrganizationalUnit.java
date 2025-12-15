package com.bdu.clearance.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "organizational_unit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationalUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String organizationId;

    @NotBlank
    private String organizationName;

    //==RELATIONS==
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_type_id")
    private OrganizationalUnitType organizationalUnitType;

    @OneToMany(mappedBy = "organizationalUnit",fetch = FetchType.LAZY)
    private List<ClearanceApproval> clearanceApprovals;
}
