import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";
import userService from "../services/userService";

const USERS_QUERY_KEY = ["users"];

/**
 * Hook for fetching all users
 */
export const useUsers = () => {
  return useQuery({
    queryKey: USERS_QUERY_KEY,
    queryFn: userService.getAllUsers,
  });
};

/**
 * Hook for fetching a single user by User ID (String)
 * @param {string} userId
 */
export const useUser = (userId) => {
  return useQuery({
    queryKey: [...USERS_QUERY_KEY, userId],
    queryFn: () => userService.getUserById(userId),
    enabled: !!userId,
  });
};

/**
 * Hook for creating a new user
 */
export const useCreateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: userService.createUser,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: USERS_QUERY_KEY });
      toast.success("User created successfully");
    },
    onError: (error) => {
      console.error("Failed to create user:", error);
      toast.error(error.response?.data?.message || "Failed to create user");
    },
  });
};

/**
 * Hook for updating an existing user
 */
export const useUpdateUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }) => userService.updateUser(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: USERS_QUERY_KEY });
      toast.success("User updated successfully");
    },
    onError: (error) => {
      console.error("Failed to update user:", error);
      toast.error(error.response?.data?.message || "Failed to update user");
    },
  });
};

/**
 * Hook for deleting a user
 */
export const useDeleteUser = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: userService.deleteUser,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: USERS_QUERY_KEY });
      toast.success("User deleted successfully");
    },
    onError: (error) => {
      console.error("Failed to delete user:", error);
      toast.error(error.response?.data?.message || "Failed to delete user");
    },
  });
};

/**
 * Hook for resetting a user's password
 */
export const useResetPassword = () => {
  return useMutation({
    mutationFn: userService.resetPassword,
    onSuccess: () => {
      toast.success("Password reset successfully");
    },
    onError: (error) => {
      console.error("Failed to reset password:", error);
      toast.error(error.response?.data?.message || "Failed to reset password");
    },
  });
};
