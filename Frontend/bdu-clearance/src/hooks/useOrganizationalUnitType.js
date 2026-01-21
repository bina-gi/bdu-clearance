import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import organizationalUnitTypeService from "../services/organizationalUnitTypeService";

const ORG_UNIT_TYPES_QUERY_KEY = ["organizationalUnitTypes"];

/**
 * Hook for fetching all organizational unit types
 */
export const useOrganizationalUnitTypes = () => {
  return useQuery({
    queryKey: ORG_UNIT_TYPES_QUERY_KEY,
    queryFn: organizationalUnitTypeService.getAllOrganizationalUnitTypes,
  });
};

/**
 * Hook for fetching a single organizational unit type by ID
 * @param {number} id - ID
 */
export const useOrganizationalUnitType = (id) => {
  return useQuery({
    queryKey: [...ORG_UNIT_TYPES_QUERY_KEY, id],
    queryFn: () =>
      organizationalUnitTypeService.getOrganizationalUnitTypeById(id),
    enabled: !!id,
  });
};

/**
 * Hook for creating a new organizational unit type
 */
export const useCreateOrganizationalUnitType = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: organizationalUnitTypeService.createOrganizationalUnitType,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ORG_UNIT_TYPES_QUERY_KEY });
    },
  });
};

/**
 * Hook for updating an existing organizational unit type
 */
export const useUpdateOrganizationalUnitType = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }) =>
      organizationalUnitTypeService.updateOrganizationalUnitType(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ORG_UNIT_TYPES_QUERY_KEY });
    },
  });
};

/**
 * Hook for deleting an organizational unit type
 */
export const useDeleteOrganizationalUnitType = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: organizationalUnitTypeService.deleteOrganizationalUnitType,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ORG_UNIT_TYPES_QUERY_KEY });
    },
  });
};
