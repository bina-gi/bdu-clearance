package com.bdu.clearance.dto.student;

import com.bdu.clearance.enums.StudentStatus;
import lombok.Data;

@Data
public class StudentResponseDto {

    private Long id;
    private Long yearOfStudy;
    private StudentStatus studentStatus;

    // User info
    private Long userId;
    private String studentId;   // user.userId
    private String fullName;

    // Advisor
    private Long advisorId;
    private String advisorName;

    //
    private Integer clearanceCount;
    private Integer propertyCount;
    private Integer lostCardReportCount;
}
