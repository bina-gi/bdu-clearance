import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";
import clearanceApprovalService from "../services/clearanceApprovalService";

const APPROVAL_KEYS = {
  pending: ["clearance-approvals", "pending"],
};

/**
 * Hook to fetch pending approvals for the current staff's org unit
 */
export const usePendingApprovals = () => {
  return useQuery({
    queryKey: APPROVAL_KEYS.pending,
    queryFn: clearanceApprovalService.getPendingApprovals,
  });
};

/**
 * Hook to process an approval (Approve/Reject)
 */
export const useProcessApproval = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, decisionDto }) =>
      clearanceApprovalService.processApproval(id, decisionDto),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: APPROVAL_KEYS.pending });
      toast.success("Decision processed successfully");
    },
    onError: (error) => {
      console.error("Failed to process approval:", error);
      toast.error(
        error.response?.data?.message || "Failed to process approval decision",
      );
    },
  });
};
