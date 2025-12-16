package com.bdu.clearance.dto.clearance;

import com.bdu.clearance.enums.Semester;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClearanceRequestDto {

    @NotNull(message = "Academic year is required")
    private Long academicYear;

    @NotNull(message = "Year of study is required")
    private Long yearOfStudy;

    @NotNull(message = "Semester is required")
    private Semester semester;

    @NotBlank(message = "Reason is required")
    private String reason;

    @NotNull(message = "Student ID is required")
    private Long studentId;
}
