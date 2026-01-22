package com.bdu.clearance.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardDto {

    private long clearanceRequested;
    private long lostCards;
    private List<ClearanceListItemDto> clearances;
    private String organizationalUnitName;
}
