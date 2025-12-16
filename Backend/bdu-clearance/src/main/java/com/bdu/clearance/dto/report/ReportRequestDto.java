package com.bdu.clearance.dto.report;

import com.bdu.clearance.enums.CardType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportRequestDto {

    @NotNull(message = "Card type is required")
    private CardType cardType;

    @NotBlank(message = "Proof document URL is required")
    private String proofDocUrl;

    @NotNull(message = "Student ID is required")
    private Long studentId;
}
