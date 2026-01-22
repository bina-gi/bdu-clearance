package com.bdu.clearance.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffDashboardDto {

    private long totalClearances;
    private long approved;
    private long pending;
    private long rejected;

    private long usersCount;
    private long organizationalUnitsCount;
    private long organizationalUnitTypesCount;
    private long rolesCount;

    private String organizationalUnitName;
}
