package com.bdu.clearance.dto.clearance;

import com.bdu.clearance.enums.Semester;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class StudentClearanceRequestDto {

        @NotNull(message = "Academic year is required")
        @Min(value = 2000, message = "Academic year must be valid")
        private Integer academicYear;

        @NotNull(message = "Year of study is required")
        @Min(value = 1, message = "Year of study must be at least 1")
        private Integer yearOfStudy;

        @NotNull(message = "Semester is required")
        private Semester semester;

        @NotBlank(message = "Clearance reason is required")
        private String clearanceReason;

        @NotNull(message = "Student ID is required")
        private Long studentId;
}
