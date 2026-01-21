import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import roleService from "../services/roleService";

const ROLES_QUERY_KEY = ["roles"];

/**
 * Hook for fetching all roles
 */
export const useRoles = () => {
  return useQuery({
    queryKey: ROLES_QUERY_KEY,
    queryFn: roleService.getAllRoles,
  });
};

/**
 * Hook for fetching a single role by ID
 * @param {number} id - Role ID
 */
export const useRole = (id) => {
  return useQuery({
    queryKey: [...ROLES_QUERY_KEY, id],
    queryFn: () => roleService.getRoleById(id),
    enabled: !!id,
  });
};

/**
 * Hook for updating an existing role
 */
export const useUpdateRole = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }) => roleService.updateRole(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ROLES_QUERY_KEY });
    },
  });
};

/**
 * Hook for deleting a role
 */
export const useDeleteRole = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: roleService.deleteRole,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ROLES_QUERY_KEY });
    },
  });
};
