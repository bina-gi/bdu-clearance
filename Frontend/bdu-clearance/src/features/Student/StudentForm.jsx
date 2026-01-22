import { useForm } from "react-hook-form";
import Button from "../../ui/components/Button";
import { useUsers } from "../../hooks/useUser";

function StudentForm({
  userId, // The Long ID of the newly created user
  userStringId, // The string ID (e.g., "U12345") for display
  userName, // Name for display
  initialData = null,
  onSubmit,
  onCancel,
  isLoading = false,
}) {
  const { data: users = [] } = useUsers();

  // Filter users to find advisors (Assuming Role Name 'ADVISOR' exists)
  // We need to check roleName. The API response for getAllUsers includes `roleName`.
  const advisors = users.filter((u) => u.roleName === "ADVISOR");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    defaultValues: {
      yearOfStudy: initialData?.yearOfStudy || "",
      studentStatus: initialData?.studentStatus || "ACTIVE",
      advisorId: initialData?.advisorId || "",
    },
  });

  const handleFormSubmit = (data) => {
    // Backend expects Long userId, yearOfStudy, studentStatus, advisorId
    onSubmit({
      userId: userId,
      yearOfStudy: parseInt(data.yearOfStudy, 10),
      studentStatus: data.studentStatus,
      advisorId: data.advisorId ? parseInt(data.advisorId, 10) : null,
    });
  };

  return (
    <form onSubmit={handleSubmit(handleFormSubmit)} className="space-y-4">
      <div className="bg-blue-50 p-4 rounded-lg mb-4 text-sm text-blue-800">
        <p className="font-semibold">Defining Student Profile for:</p>
        <p>
          {userName} ({userStringId})
        </p>
      </div>

      {/* Year of Study */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Year of Study
        </label>
        <input
          type="number"
          min="1"
          max="7"
          {...register("yearOfStudy", {
            required: "Year of study is required",
            min: { value: 1, message: "Minimum year is 1" },
            max: { value: 7, message: "Maximum year is 7" },
          })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 ${
            errors.yearOfStudy ? "border-red-500" : "border-gray-200"
          }`}
        />
        {errors.yearOfStudy && (
          <p className="mt-1 text-xs text-red-500">
            {errors.yearOfStudy.message}
          </p>
        )}
      </div>

      {/* Student Status */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Student Status
        </label>
        <select
          {...register("studentStatus", { required: "Status is required" })}
          className="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white"
        >
          <option value="ACTIVE">ACTIVE</option>
          <option value="GRADUATED">GRADUATED</option>
          <option value="WITHDRAWN">WITHDRAWN</option>
          <option value="DISMISSED">DISMISSED</option>
        </select>
      </div>

      {/* Advisor */}
      <div>
        <label className="block text-sm font-medium text-gray-700 mb-1">
          Advisor
        </label>
        <select
          {...register("advisorId", { required: "Advisor is required" })}
          className={`w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 bg-white ${
            errors.advisorId ? "border-red-500" : "border-gray-200"
          }`}
        >
          <option value="">Select an advisor</option>
          {advisors.map((advisor) => (
            <option key={advisor.id} value={advisor.id}>
              {advisor.firstName} {advisor.middleName} {advisor.lastName}
            </option>
          ))}
        </select>
        {errors.advisorId && (
          <p className="mt-1 text-xs text-red-500">
            {errors.advisorId.message}
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
          Skip / Cancel
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
              ? "Update Student Details"
              : "Create Student Details"}
        </Button>
      </div>
    </form>
  );
}

export default StudentForm;
