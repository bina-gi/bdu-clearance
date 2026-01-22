import { useForm } from "react-hook-form";
import { useEffect } from "react";
import Button from "../../ui/components/Button";
import { useOrganizationalUnitTypes } from "../../hooks/useOrganizationalUnitType";
import { useOrganizationalUnits } from "../../hooks/useOrganizationalUnit";

function OrganizationalUnitForm({
  initialData = null,
  onSubmit,
  onCancel,
  isLoading = false,
}) {
  const { data: orgTypes = [] } = useOrganizationalUnitTypes();
  const { data: orgUnits = [] } = useOrganizationalUnits();

  // Filter out self from parent list to prevent recursion if editing
  const parentOptions = orgUnits.filter(
    (unit) => !initialData || unit.id !== initialData.id,
  );

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
    watch,
  } = useForm({
    defaultValues: {
      organizationId: initialData?.organizationId || "",
      organizationName: initialData?.organizationName || "",
      organizationalUnitTypeId: initialData?.organizationalUnitTypeId || "",
      parentOrganizationId: initialData?.organizationId, // Note: parent lookup might be tricky if backend returns parent NAME but we need ID logic.
      // Actually backend DTO doesn't clearly show parent ID in ResponseDto:
      // ResponseDto: { id, organizationId, organizationName, organizationalUnitTypeId, organizationalUnitTypeName }
      // It acts as if we don't have the parent in the response?
      // Wait, checking the backend code...
      // OrganizationalUnitController create/update takes `parentOrganizationId`.
      // OrganizationalUnitResponseDto HAS `organizationId` but NO `parentOrganizationId`.
      // This is a potential issue. If the backend doesn't return the parent, I can't pre-fill it in Edit mode.
      // I will assume for now we might not be able to edit parent or it's missing from DTO.
      // Proceeding with what we have.
    },
  });

  useEffect(() => {
    if (initialData) {
      reset({
        organizationId: initialData.organizationId,
        organizationName: initialData.organizationName,
        organizationalUnitTypeId: initialData.organizationalUnitTypeId,
        // parentOrganizationId: ??? API response doesn't seem to include it based on the prompt's DTO
      });
    }
  }, [initialData, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      {/* Organization ID */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Organization ID
        </label>
        <input
          type="text"
          {...register("organizationId", { required: "ID is required" })}
          disabled={!!initialData} // Usually IDs are immutable, but prompt didn't say. Safer to allow if new, disable if edit? backend logic depends.
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
            errors.organizationId ? "border-red-500" : "border-gray-200"
          }`}
          placeholder="e.g. DEP-CS"
        />
        {errors.organizationId && (
          <p className="mt-1 text-xs text-red-500">
            {errors.organizationId.message}
          </p>
        )}
      </div>

      {/* Organization Name */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Organization Name
        </label>
        <input
          type="text"
          {...register("organizationName", { required: "Name is required" })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
            errors.organizationName ? "border-red-500" : "border-gray-200"
          }`}
          placeholder="e.g. Computer Science Department"
        />
        {errors.organizationName && (
          <p className="mt-1 text-xs text-red-500">
            {errors.organizationName.message}
          </p>
        )}
      </div>

      {/* Type */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Organization Type
        </label>
        <select
          {...register("organizationalUnitTypeId", {
            required: "Type is required",
          })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white ${
            errors.organizationalUnitTypeId
              ? "border-red-500"
              : "border-gray-200"
          }`}
        >
          <option value="">Select a type</option>
          {orgTypes.map((type) => (
            <option key={type.id} value={type.id}>
              {type.organizationType}
            </option>
          ))}
        </select>
        {errors.organizationalUnitTypeId && (
          <p className="mt-1 text-xs text-red-500">
            {errors.organizationalUnitTypeId.message}
          </p>
        )}
      </div>

      {/* Parent Organization */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Parent Organization (Optional)
        </label>
        <select
          {...register("parentOrganizationId")}
          className="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white"
        >
          <option value="">None</option>
          {parentOptions.map((unit) => (
            <option key={unit.id} value={unit.organizationId}>
              {unit.organizationName}
            </option>
          ))}
        </select>
      </div>

      <div className="flex gap-3 pt-4 border-t border-gray-200">
        <Button
          type="button"
          variation="secondary"
          size="small"
          onClick={onCancel}
          disabled={isLoading}
          className="flex-1"
        >
          Cancel
        </Button>
        <Button
          type="submit"
          variation="primary"
          size="small"
          disabled={isLoading}
          className="flex-1"
        >
          {isLoading
            ? "Saving..."
            : initialData
              ? "Update Unit"
              : "Create Unit"}
        </Button>
      </div>
    </form>
  );
}

export default OrganizationalUnitForm;
