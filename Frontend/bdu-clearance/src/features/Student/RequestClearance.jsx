import { useState } from "react";
import { useForm } from "react-hook-form";
import { Send, FileText, Calendar, BookOpen } from "lucide-react";
import useAuth from "../../hooks/useAuth";
import { useStudent } from "../../hooks/useStudent";
import { useCreateClearance } from "../../hooks/useClearance";
import PageHeader from "../../ui/components/PageHeader";
import Button from "../../ui/components/Button";
import Card from "../../ui/components/Card";
import Input from "../../ui/components/Input";

export default function RequestClearance() {
  const { user } = useAuth();
  const { data: studentData, isLoading: isStudentLoading } = useStudent(
    user?.username,
  );
  const createClearanceMutation = useCreateClearance();

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({
    defaultValues: {
      academicYear: new Date().getFullYear(),
      semester: "I",
      reason: "",
    },
  });

  const onSubmit = async (data) => {
    if (!studentData?.id) {
      return;
    }

    const payload = {
      academicYear: parseInt(data.academicYear),
      semester: data.semester,
      yearOfStudy: studentData.yearOfStudy,
      clearanceReason: data.reason,
      studentId: studentData.id, // We need the Long ID here, not String ID
    };

    await createClearanceMutation.mutateAsync(payload);
    reset(); // Reset form on success
  };

  if (isStudentLoading) {
    return (
      <div className="p-8 text-center text-gray-500">
        Loading student profile...
      </div>
    );
  }

  if (!studentData) {
    return (
      <div className="p-8 text-center text-red-500 bg-red-50 rounded-lg m-8">
        Student profile not found. Please contact the registrar.
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      <div className="max-w-3xl mx-auto">
        <PageHeader
          title="Request Clearance"
          description="Submit a new clearance request for the current semester."
        />

        <div className="mt-8 grid gap-8">
          {/* Student Info Card (ReadOnly) */}
          <Card className="p-6 bg-blue-50 border-blue-100">
            <h3 className="text-lg font-semibold text-blue-900 mb-4 flex items-center gap-2">
              <UserIcon className="text-blue-600" size={20} />
              Student Details
            </h3>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 text-sm">
              <div>
                <span className="block text-blue-600 font-medium">
                  Full Name
                </span>
                <span className="text-blue-900 font-semibold">
                  {studentData.firstName} {studentData.lastName}
                </span>
              </div>
              <div>
                <span className="block text-blue-600 font-medium">
                  Student ID
                </span>
                <span className="text-blue-900 font-semibold">
                  {studentData.studentId}
                </span>
              </div>
              <div>
                <span className="block text-blue-600 font-medium">
                  Department
                </span>
                <span className="text-blue-900 font-semibold">
                  {studentData.departmentName || "N/A"}
                </span>
              </div>
              <div>
                <span className="block text-blue-600 font-medium">
                  Year of Study
                </span>
                <span className="text-blue-900 font-semibold">
                  {studentData.yearOfStudy}
                </span>
              </div>
            </div>
          </Card>

          {/* Request Form */}
          <Card className="p-0 overflow-hidden">
            <div className="bg-white p-6 md:p-8">
              <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  {/* Academic Year */}
                  <Input
                    label="Academic Year"
                    type="number"
                    icon={Calendar}
                    registration={register("academicYear", {
                      required: "Academic Year is required",
                      min: { value: 2000, message: "Invalid year" },
                    })}
                    error={errors.academicYear}
                  />

                  {/* Semester */}
                  <div className="space-y-2">
                    <label className="block text-sm font-medium text-gray-700">
                      Semester
                    </label>
                    <div className="relative">
                      <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400">
                        <BookOpen size={18} />
                      </div>
                      <select
                        {...register("semester", {
                          required: "Semester is required",
                        })}
                        className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-sm"
                      >
                        <option value="I">Semester I</option>
                        <option value="II">Semester II</option>
                        <option value="SUMMER">Summer</option>
                      </select>
                    </div>
                    {errors.semester && (
                      <p className="text-red-500 text-xs mt-1">
                        {errors.semester.message}
                      </p>
                    )}
                  </div>
                </div>

                {/* Reason */}
                <div className="space-y-2">
                  <label className="block text-sm font-medium text-gray-700">
                    Reason for Clearance
                  </label>
                  <div className="relative">
                    <div className="absolute top-3 left-3 pointer-events-none text-gray-400">
                      <FileText size={18} />
                    </div>
                    <textarea
                      {...register("reason", {
                        required: "Reason is required",
                      })}
                      rows={4}
                      className="block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-sm transition-colors resize-none"
                      placeholder="e.g., Graduation, Withdrawal, Transfer..."
                    ></textarea>
                  </div>
                  {errors.reason && (
                    <p className="text-red-500 text-xs mt-1">
                      {errors.reason.message}
                    </p>
                  )}
                </div>

                <div className="pt-4 flex justify-end">
                  <Button
                    variation="primary"
                    type="submit"
                    isLoading={createClearanceMutation.isPending}
                    className="w-full md:w-auto flex justify-center items-center gap-2"
                  >
                    <Send size={18} />
                    Submit Request
                  </Button>
                </div>
              </form>
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}

// Simple Icon component for local use (avoiding import clutter if not reused)
const UserIcon = ({ size, className }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width={size}
    height={size}
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
    className={className}
  >
    <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"></path>
    <circle cx="12" cy="7" r="4"></circle>
  </svg>
);
