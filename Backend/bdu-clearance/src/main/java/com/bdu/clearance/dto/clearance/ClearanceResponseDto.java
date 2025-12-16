package com.bdu.clearance.dto.clearance;

import com.bdu.clearance.dto.clearanceApproval.ClearanceApprovalResponseDto;
import com.bdu.clearance.enums.Semester;
import lombok.Data;

import java.util.List;

@Data
public class ClearanceResponseDto {

    private Long id;
    private Long academicYear;
    private Long yearOfStudy;
    private Semester semester;
    private String reason;

    // Student info
    private Long studentId;
    private String studentName;

    // Approval info
    private Integer totalApprovals;
    private Integer approvedCount;
    private Integer pendingCount;

    private List<ClearanceApprovalResponseDto> approvals;
}
