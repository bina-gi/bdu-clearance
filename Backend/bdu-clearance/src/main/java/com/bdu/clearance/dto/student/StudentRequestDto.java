package com.bdu.clearance.dto.student;


import com.bdu.clearance.enums.StudentStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class StudentRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Year of study is required")
    @Min(value = 1, message = "Year of study must be at least 1")
    private Long yearOfStudy;

    @NotNull(message = "Student status is required")
    private StudentStatus studentStatus;

    // Optional – advisor assignment
    private Long advisorId;
}
