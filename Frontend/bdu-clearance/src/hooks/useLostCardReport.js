import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";
import lostCardReportService from "../services/lostCardReportService";

const LOST_CARD_KEYS = {
  all: ["lostCardReports"],
  byStudent: (studentId) => ["lostCardReports", "student", studentId],
  byId: (id) => ["lostCardReports", id],
};

/**
 * Hook to create a lost card report
 */
export const useCreateLostCardReport = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: lostCardReportService.createLostCardReport,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: LOST_CARD_KEYS.all });
      toast.success("Lost card report submitted successfully");
    },
    onError: (error) => {
      console.error("Failed to submit lost card report:", error);
      toast.error(
        error.response?.data?.message || "Failed to submit lost card report",
      );
    },
  });
};

/**
 * Hook to fetch lost card reports for a specific student
 * @param {number} studentId
 */
export const useStudentLostCardReports = (studentId) => {
  return useQuery({
    queryKey: LOST_CARD_KEYS.byStudent(studentId),
    queryFn: () =>
      lostCardReportService.getLostCardReportsByStudentId(studentId),
    enabled: !!studentId,
  });
};

/**
 * Hook to fetch all lost card reports (admin)
 */
export const useAllLostCardReports = () => {
  return useQuery({
    queryKey: LOST_CARD_KEYS.all,
    queryFn: lostCardReportService.getAllLostCardReports,
  });
};

/**
 * Hook to upload proof image
 */
export const useUploadProofImage = () => {
  return useMutation({
    mutationFn: lostCardReportService.uploadProofImage,
    onError: (error) => {
      console.error("Failed to upload image:", error);
      toast.error("Failed to upload proof image");
    },
  });
};

/**
 * Hook to delete a lost card report
 */
export const useDeleteLostCardReport = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: lostCardReportService.deleteLostCardReport,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: LOST_CARD_KEYS.all });
      toast.success("Lost card report deleted");
    },
    onError: (error) => {
      console.error("Failed to delete report:", error);
      toast.error("Failed to delete lost card report");
    },
  });
};

/**
 * Hook to process (approve/reject) a lost card report
 */
export const useProcessLostCardReport = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, status, processedByUserId }) =>
      lostCardReportService.processLostCardReport(
        id,
        status,
        processedByUserId,
      ),
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: LOST_CARD_KEYS.all });
      const action = variables.status === "APPROVED" ? "approved" : "rejected";
      toast.success(`Lost card report ${action} successfully`);
    },
    onError: (error) => {
      console.error("Failed to process report:", error);
      toast.error("Failed to process lost card report");
    },
  });
};
