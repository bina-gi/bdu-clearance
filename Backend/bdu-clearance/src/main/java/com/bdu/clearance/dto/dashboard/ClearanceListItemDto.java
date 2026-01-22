package com.bdu.clearance.dto.dashboard;

import com.bdu.clearance.enums.ClearanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClearanceListItemDto {

    private Long id;
    private ClearanceStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime completionDate;
}
