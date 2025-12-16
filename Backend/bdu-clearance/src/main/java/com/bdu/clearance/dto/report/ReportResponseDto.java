package com.bdu.clearance.dto.report;

import com.bdu.clearance.enums.ApprovalStatus;
import com.bdu.clearance.enums.CardType;
import lombok.Data;

@Data
public class ReportResponseDto {

    private Long id;

    private CardType cardType;
    private String proofDocUrl;
    private ApprovalStatus status;

    private Long studentId;
    private String studentName;
}
