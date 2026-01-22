import { useForm } from "react-hook-form";
import { useEffect } from "react";
import Button from "../../ui/components/Button";
import { useRoles } from "../../hooks/useRole";
import { useOrganizationalUnits } from "../../hooks/useOrganizationalUnit";

function UserForm({
  initialData = null,
  onSubmit,
  onCancel,
  isLoading = false,
}) {
  const { data: roles = [] } = useRoles();
  const { data: orgUnits = [] } = useOrganizationalUnits();

  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      userId: initialData?.userId || "",
      firstName: initialData?.firstName || "",
      middleName: initialData?.middleName || "",
      lastName: initialData?.lastName || "",
      roleId: initialData?.roleId || "",
      organizationalUnitId: initialData?.organizationalUnitId || "",
      isActive: initialData?.isActive ?? true,
    },
  });

  useEffect(() => {
    if (initialData) {
      reset({
        userId: initialData.userId,
        firstName: initialData.firstName,
        middleName: initialData.middleName,
        lastName: initialData.lastName,
        roleId: initialData.roleId,
        organizationalUnitId: initialData.organizationalUnitId,
        isActive: initialData.isActive,
      });
    }
  }, [initialData, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      {/* User ID */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          User ID
        </label>
        <input
          type="text"
          {...register("userId", { required: "User ID is required" })}
          disabled={!!initialData} // Usually User IDs are immutable
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
            errors.userId ? "border-red-500" : "border-gray-200"
          } ${!!initialData ? "bg-gray-100 cursor-not-allowed" : ""}`}
        />
        {errors.userId && (
          <p className="mt-1 text-xs text-red-500">{errors.userId.message}</p>
        )}
      </div>

      {/* Name Fields */}
      <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">
            First Name
          </label>
          <input
            type="text"
            {...register("firstName", { required: "Required" })}
            className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
              errors.firstName ? "border-red-500" : "border-gray-200"
            }`}
          />
          {errors.firstName && (
            <p className="mt-1 text-xs text-red-500">
              {errors.firstName.message}
            </p>
          )}
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">
            Middle Name
          </label>
          <input
            type="text"
            {...register("middleName", { required: "Required" })}
            className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
              errors.middleName ? "border-red-500" : "border-gray-200"
            }`}
          />
          {errors.middleName && (
            <p className="mt-1 text-xs text-red-500">
              {errors.middleName.message}
            </p>
          )}
        </div>
        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">
            Last Name
          </label>
          <input
            type="text"
            {...register("lastName", { required: "Required" })}
            className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
              errors.lastName ? "border-red-500" : "border-gray-200"
            }`}
          />
          {errors.lastName && (
            <p className="mt-1 text-xs text-red-500">
              {errors.lastName.message}
            </p>
          )}
        </div>
      </div>

      {/* Role */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Role
        </label>
        <select
          {...register("roleId", { required: "Role is required" })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white ${
            errors.roleId ? "border-red-500" : "border-gray-200"
          }`}
        >
          <option value="">Select a role</option>
          {roles.map((role) => (
            <option key={role.id} value={role.id}>
              {role.roleName}
            </option>
          ))}
        </select>
        {errors.roleId && (
          <p className="mt-1 text-xs text-red-500">{errors.roleId.message}</p>
        )}
      </div>

      {/* Organization Unit */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Organization Unit
        </label>
        <select
          {...register("organizationalUnitId", {
            required: "Unit is required",
          })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white ${
            errors.organizationalUnitId ? "border-red-500" : "border-gray-200"
          }`}
        >
          <option value="">Select an organizational unit</option>
          {orgUnits.map((unit) => (
            <option key={unit.id} value={unit.id}>
              {unit.organizationName}
            </option>
          ))}
        </select>
        {errors.organizationalUnitId && (
          <p className="mt-1 text-xs text-red-500">
            {errors.organizationalUnitId.message}
          </p>
        )}
      </div>

      {/* Active Status */}
      <div className="flex items-center gap-2">
        <input
          type="checkbox"
          id="isActive"
          {...register("isActive")}
          className="w-4 h-4 text-blue-600 border-gray-300 rounded focus:ring-blue-500"
        />
        <label htmlFor="isActive" className="text-sm font-medium text-gray-700">
          Active Account
        </label>
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
              ? "Update User"
              : "Create User"}
        </Button>
      </div>
    </form>
  );
}

export default UserForm;
