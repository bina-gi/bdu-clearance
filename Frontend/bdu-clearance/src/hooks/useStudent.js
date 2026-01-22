import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";
import studentService from "../services/studentService";

const STUDENTS_QUERY_KEY = ["students"];

/**
 * Hook for creating a new student
 */
export const useCreateStudent = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: studentService.createStudent,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: STUDENTS_QUERY_KEY });
      toast.success("Student profile created successfully");
    },
    onError: (error) => {
      console.error("Failed to create student:", error);
      toast.error(
        error.response?.data?.message || "Failed to create student profile",
      );
    },
  });
};

/**
 * Hook for fetching all students
 */
export const useStudents = () => {
  return useQuery({
    queryKey: STUDENTS_QUERY_KEY,
    queryFn: studentService.getAllStudents,
  });
};

/**
 * Hook for fetching a single student by ID
 */
export const useStudent = (studentId) => {
  return useQuery({
    queryKey: [STUDENTS_QUERY_KEY, studentId],
    queryFn: () => studentService.getStudent(studentId),
    enabled: !!studentId,
  });
};

/**
 * Hook for updating a student
 */
export const useUpdateStudent = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }) => studentService.updateStudent(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: STUDENTS_QUERY_KEY });
      toast.success("Student profile updated successfully");
    },
    onError: (error) => {
      console.error("Failed to update student:", error);
      toast.error(
        error.response?.data?.message || "Failed to update student profile",
      );
    },
  });
};

/**
 * Hook for deleting a student
 */
export const useDeleteStudent = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: studentService.deleteStudent,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: STUDENTS_QUERY_KEY });
      // Toast handled in logic because this is part of a larger flow usually
      // But we can add it here too if reused.
    },
    onError: (error) => {
      console.error("Failed to delete student:", error);
      toast.error(
        error.response?.data?.message || "Failed to delete student profile",
      );
    },
  });
};
