package com.bdu.clearance.dto.student;

import com.bdu.clearance.enums.*;
import lombok.Data;

@Data
public class StudentRequestDto {

    private String userId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Campus campus;
    private UserRole role;
    private Boolean isActive;

    private Faculty faculty;
    private Department department;
    private String advisor;
    private int year;
}
