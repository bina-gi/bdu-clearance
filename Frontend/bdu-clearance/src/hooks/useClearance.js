import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";
import clearanceService from "../services/clearanceService";

const CLEARANCE_KEYS = {
  all: ["clearances"],
  byStudent: (studentId) => ["clearances", "student", studentId],
};

/**
 * Hook to create a clearance request
 */
export const useCreateClearance = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: clearanceService.createClearance,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: CLEARANCE_KEYS.all });
      toast.success("Clearance request submitted successfully");
    },
    onError: (error) => {
      console.error("Failed to submit clearance:", error);
      toast.error(
        error.response?.data?.message || "Failed to submit clearance request",
      );
    },
  });
};

/**
 * Hook to fetch clearances for a specific student
 * @param {string} studentId
 */
export const useStudentClearances = (studentId) => {
  return useQuery({
    queryKey: CLEARANCE_KEYS.byStudent(studentId),
    queryFn: () => clearanceService.getClearanceByStudentId(studentId),
    enabled: !!studentId,
  });
};

/**
 * Hook to download PDF
 */
export const useDownloadClearancePdf = () => {
  return useMutation({
    mutationFn: clearanceService.downloadClearancePdf,
    onError: (error) => {
      console.error("Failed to download PDF:", error);
      toast.error("Failed to download clearance PDF");
    },
  });
};
