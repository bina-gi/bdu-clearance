import { useForm } from "react-hook-form";
import { useEffect } from "react";
import Button from "../../ui/components/Button";

function OrganizationalUnitTypeForm({
  initialData = null,
  onSubmit,
  onCancel,
  isLoading = false,
}) {
  const {
    register,
    handleSubmit,
    reset,
    formState: { errors },
  } = useForm({
    defaultValues: {
      organizationType: initialData?.organizationType || "",
    },
  });

  useEffect(() => {
    if (initialData) {
      reset({ organizationType: initialData.organizationType });
    }
  }, [initialData, reset]);

  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
      <div>
        <label
          htmlFor="organizationType"
          className="block text-sm font-medium text-gray-700 mb-1"
        >
          Organization Type Name
        </label>
        <input
          id="organizationType"
          type="text"
          {...register("organizationType", {
            required: "Organization type name is required",
          })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
            errors.organizationType ? "border-red-500" : "border-gray-200"
          }`}
          placeholder="e.g. Department, Faculty, Library"
        />
        {errors.organizationType && (
          <p className="mt-1 text-xs text-red-500">
            {errors.organizationType.message}
          </p>
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
          {isLoading
            ? "Saving..."
            : initialData
              ? "Update Type"
              : "Create Type"}
        </Button>
      </div>
    </form>
  );
}

export default OrganizationalUnitTypeForm;
