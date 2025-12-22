package com.bdu.clearance.models;

import com.bdu.clearance.enums.ApprovalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "clearance_approval")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearanceApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    private String remarks;

    /** Order in which this approval should be processed (1 = first) */
    private Integer approvalOrder;

    /** Whether this approval is required for clearance completion */
    private Boolean isRequired = true;

    /** When the approval decision was made */
    private LocalDateTime approvalDate;

    // ==RELATIONS==
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clearance_id", nullable = false)
    private Clearance clearance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizational_unit_id", nullable = false)
    private OrganizationalUnit organizationalUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by")
    private Users approvedBy;

}
