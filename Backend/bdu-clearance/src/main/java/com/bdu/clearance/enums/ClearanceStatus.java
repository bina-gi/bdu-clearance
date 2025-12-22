package com.bdu.clearance.enums;

/**
 * Represents the lifecycle status of a clearance request.
 * Supports state machine transitions for workflow management.
 */
public enum ClearanceStatus {
    PENDING, // Just submitted, awaiting approvals
    IN_PROGRESS, // At least one approval started/under review
    COMPLETED, // All required approvals complete
    REJECTED, // Any approver rejected the clearance
    CANCELLED // Student cancelled the request
}