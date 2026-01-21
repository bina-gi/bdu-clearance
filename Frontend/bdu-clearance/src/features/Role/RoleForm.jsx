import { useForm } from "react-hook-form";
import { useEffect } from "react";
import Button from "../../ui/components/Button";

// Available user roles from the backend enum
const USER_ROLES = [
  "ADMIN",
  "STUDENT",
  "STAFF",
  "ADVISOR",
  "REGISTRAR",
  "LIBRARY",
  "CAFETERIA",
  "DORMITORY",
  "DEPARTMENT",
];

function RoleForm({ role = null, onSubmit, onCancel, isLoading = false }) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      roleName: role?.roleName || "",
    },
  });

  useEffect(() => {
    if (role) {
      reset({ roleName: role.roleName });
    }
  }, [role, reset]);

  const handleFormSubmit = (data) => {
    onSubmit(data);
  };

  return (
    <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
      <div>
        <label
          htmlFor="roleName"
          className="block text-sm font-medium text-gray-700 mb-1"
        >
          Role Name
        </label>
        <select
          id="roleName"
          {...register("roleName", { required: "Role name is required" })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
            errors.roleName ? "border-red-500" : "border-gray-200"
          }`}
        >
          <option value="">Select a role</option>
          {USER_ROLES.map((roleName) => (
            <option key={roleName} value={roleName}>
              {roleName}
            </option>
          ))}
        </select>
        {errors.roleName && (
          <p className="mt-1 text-xs text-red-500">{errors.roleName.message}</p>
        )}
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
          {isLoading ? "Saving..." : "Update Role"}
        </Button>
      </div>
    </form>
  );
}

export default RoleForm;
