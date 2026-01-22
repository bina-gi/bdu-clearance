import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import toast from "react-hot-toast";
import organizationalUnitService from "../services/organizationalUnitService";

const ORG_UNITS_QUERY_KEY = ["organizationalUnits"];

/**
 * Hook for fetching all organizational units
 */
export const useOrganizationalUnits = () => {
  return useQuery({
    queryKey: ORG_UNITS_QUERY_KEY,
    queryFn: organizationalUnitService.getAllOrganizationalUnits,
  });
};

/**
 * Hook for fetching a single organizational unit by ID
 * @param {number} id - ID
 */
export const useOrganizationalUnit = (id) => {
  return useQuery({
    queryKey: [...ORG_UNITS_QUERY_KEY, id],
    queryFn: () => organizationalUnitService.getOrganizationalUnitById(id),
    enabled: !!id,
  });
};

/**
 * Hook for creating a new organizational unit
 */
export const useCreateOrganizationalUnit = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: organizationalUnitService.createOrganizationalUnit,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ORG_UNITS_QUERY_KEY });
      toast.success("Organizational unit created successfully");
    },
    onError: (error) => {
      console.error("Failed to create org unit:", error);
      toast.error(
        error.response?.data?.message || "Failed to create organizational unit",
      );
    },
  });
};

/**
 * Hook for updating an existing organizational unit
 */
export const useUpdateOrganizationalUnit = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ id, data }) =>
      organizationalUnitService.updateOrganizationalUnit(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ORG_UNITS_QUERY_KEY });
      toast.success("Organizational unit updated successfully");
    },
    onError: (error) => {
      console.error("Failed to update org unit:", error);
      toast.error(
        error.response?.data?.message || "Failed to update organizational unit",
      );
    },
  });
};

/**
 * Hook for deleting an organizational unit
 */
export const useDeleteOrganizationalUnit = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: organizationalUnitService.deleteOrganizationalUnit,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ORG_UNITS_QUERY_KEY });
      toast.success("Organizational unit deleted successfully");
    },
    onError: (error) => {
      console.error("Failed to delete org unit:", error);
      toast.error(
        error.response?.data?.message || "Failed to delete organizational unit",
      );
    },
  });
};
