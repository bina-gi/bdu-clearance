package com.bdu.clearance.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String parentOrganizationId;

    // ==RELATIONS==
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_type_id")
    private OrganizationalUnitType organizationalUnitType;

    @OneToMany(mappedBy = "organizationalUnit", fetch = FetchType.LAZY)
    private List<ClearanceApproval> clearanceApprovals;

    @OneToMany(mappedBy = "organizationalUnit", fetch = FetchType.LAZY)
    private List<Users> users;

    @OneToMany(mappedBy = "organizationalUnit", fetch = FetchType.LAZY)
    private List<Property> properties;
}
