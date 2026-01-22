import api from "./api";

const BASE_URL = "/clearance-approvals";

/**
 * Get pending approvals for the current user's organizational unit (Staff/Advisor)
 * @returns {Promise<Array>}
 */
const getPendingApprovals = async () => {
  const response = await api.get(`${BASE_URL}/pending`);
  return response.data;
};

/**
 * Process an approval decision (Approve/Reject)
 * @param {number} id - ClearanceApproval ID
 * @param {Object} decisionDto - { decision: "APPROVED" | "REJECTED", remarks: string, approvedByUserId: number }
 * @returns {Promise<void>}
 */
const processApproval = async (id, decisionDto) => {
  await api.put(`${BASE_URL}/${id}/process`, decisionDto);
};

const clearanceApprovalService = {
  getPendingApprovals,
  processApproval,
};

export default clearanceApprovalService;
