package com.bdu.clearance.models;

import com.bdu.clearance.enums.ApprovalStatus;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clearance_approval")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearanceApproval{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private String remarks;
    // ==RELATIONS==
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clearance_id", nullable = false)
    private Clearance clearance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_unit_id",nullable = false)
    private OrganizationalUnit organizationalUnit;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by",nullable = false)
    private Users approvedBy;

}

