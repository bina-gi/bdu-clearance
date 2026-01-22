import { useState, useRef } from "react";
import { useForm } from "react-hook-form";
import {
  CreditCard,
  Upload,
  Send,
  X,
  Clock,
  CheckCircle,
  XCircle,
  AlertCircle,
} from "lucide-react";
import useAuth from "../../hooks/useAuth";
import { useStudent } from "../../hooks/useStudent";
import {
  useCreateLostCardReport,
  useStudentLostCardReports,
  useUploadProofImage,
} from "../../hooks/useLostCardReport";
import PageHeader from "../../ui/components/PageHeader";
import Button from "../../ui/components/Button";
import Card from "../../ui/components/Card";

// Card type options matching backend enum
const CARD_TYPES = [
  { value: "ID_CARD", label: "Student ID Card" },
  { value: "MEAL_CARD", label: "Meal Card" },
];

// Status badge component
const StatusBadge = ({ status }) => {
  const statusConfig = {
    PENDING: {
      icon: Clock,
      className: "bg-yellow-100 text-yellow-800 border-yellow-200",
      label: "Pending",
    },
    UNDER_REVIEW: {
      icon: AlertCircle,
      className: "bg-blue-100 text-blue-800 border-blue-200",
      label: "Under Review",
    },
    APPROVED: {
      icon: CheckCircle,
      className: "bg-green-100 text-green-800 border-green-200",
      label: "Approved",
    },
    REJECTED: {
      icon: XCircle,
      className: "bg-red-100 text-red-800 border-red-200",
      label: "Rejected",
    },
  };

  const config = statusConfig[status] || statusConfig.PENDING;
  const Icon = config.icon;

  return (
    <span
      className={`inline-flex items-center gap-1.5 px-2.5 py-1 text-xs font-medium rounded-full border ${config.className}`}
    >
      <Icon size={14} />
      {config.label}
    </span>
  );
};

export default function ReportLostCard() {
  const { user } = useAuth();
  const { data: studentData, isLoading: isStudentLoading } = useStudent(
    user?.username,
  );
  const createReportMutation = useCreateLostCardReport();
  const uploadImageMutation = useUploadProofImage();
  const { data: reports, isLoading: isReportsLoading } =
    useStudentLostCardReports(studentData?.id);

  const [selectedImage, setSelectedImage] = useState(null);
  const [imagePreview, setImagePreview] = useState(null);
  const fileInputRef = useRef(null);

  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm({
    defaultValues: {
      cardType: "ID_CARD",
    },
  });

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    if (file) {
      // Validate file type
      if (!file.type.startsWith("image/")) {
        alert("Please select an image file");
        return;
      }
      // Validate file size (max 5MB)
      if (file.size > 5 * 1024 * 1024) {
        alert("Image size must be less than 5MB");
        return;
      }
      setSelectedImage(file);
      setImagePreview(URL.createObjectURL(file));
    }
  };

  const clearImage = () => {
    setSelectedImage(null);
    setImagePreview(null);
    if (fileInputRef.current) {
      fileInputRef.current.value = "";
    }
  };

  const onSubmit = async (data) => {
    if (!studentData?.id) {
      return;
    }

    if (!selectedImage) {
      alert("Please upload a proof image");
      return;
    }

    try {
      // First upload the image
      const imageUrl = await uploadImageMutation.mutateAsync(selectedImage);

      // Then create the report with the image URL
      const payload = {
        cardType: data.cardType,
        proofDocUrl: imageUrl,
        studentId: studentData.id,
      };

      await createReportMutation.mutateAsync(payload);

      // Reset form on success
      reset();
      clearImage();
    } catch (error) {
      console.error("Failed to submit report:", error);
    }
  };

  const isSubmitting =
    createReportMutation.isPending || uploadImageMutation.isPending;

  if (isStudentLoading) {
    return (
      <div className="p-8 text-center text-gray-500">
        Loading student profile...
      </div>
    );
  }

  if (!studentData) {
    return (
      <div className="p-8 text-center text-red-500  rounded-lg m-8">
        Student profile not found. Please contact the registrar.
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      <div className="max-w-4xl mx-auto">
        <PageHeader
          title="Report Lost Card"
          description="Submit a report for your lost student ID or meal card."
        />

        <div className="mt-8 grid gap-8">
          {/* Student Info Card (ReadOnly) */}
          <Card className="p-6 bg-gradient-to-br from-blue-50 to-indigo-50 border-blue-100">
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

          {/* Report Form */}
          <Card className="p-0 overflow-hidden shadow-lg">
            <div className="bg-gradient-to-r from-blue-700 to-blue-400 px-6 py-4">
              <h3 className="text-white font-semibold text-lg flex items-center gap-2">
                <CreditCard size={20} />
                Report Lost Card
              </h3>
            </div>
            <div className="bg-white p-6 md:p-8">
              <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
                {/* Card Type */}
                <div className="space-y-2">
                  <label className="block text-sm font-medium text-gray-700">
                    Card Type <span className="text-red-500">*</span>
                  </label>
                  <div className="relative">
                    <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-gray-400">
                      <CreditCard size={18} />
                    </div>
                    <select
                      {...register("cardType", {
                        required: "Card type is required",
                      })}
                      className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 text-sm transition-all bg-white"
                    >
                      {CARD_TYPES.map((type) => (
                        <option key={type.value} value={type.value}>
                          {type.label}
                        </option>
                      ))}
                    </select>
                  </div>
                  {errors.cardType && (
                    <p className="text-red-500 text-xs mt-1">
                      {errors.cardType.message}
                    </p>
                  )}
                </div>

                {/* Image Upload */}
                <div className="space-y-2">
                  <label className="block text-sm font-medium text-gray-700">
                    Proof Document <span className="text-red-500">*</span>
                  </label>
                  <p className="text-xs text-gray-500 mb-2">
                    Upload a photo of your police report or any other proof
                    document
                  </p>

                  {!imagePreview ? (
                    <div
                      onClick={() => fileInputRef.current?.click()}
                      className="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center cursor-pointer hover:border-blue-400 hover:bg-blue-50 transition-all"
                    >
                      <Upload
                        size={40}
                        className="mx-auto text-gray-400 mb-3"
                      />
                      <p className="text-sm text-gray-600">
                        Click to upload or drag and drop
                      </p>
                      <p className="text-xs text-gray-400 mt-1">
                        PNG, JPG up to 5MB
                      </p>
                    </div>
                  ) : (
                    <div className="relative border rounded-lg overflow-hidden">
                      <img
                        src={imagePreview}
                        alt="Preview"
                        className="w-full h-48 object-cover"
                      />
                      <button
                        type="button"
                        onClick={clearImage}
                        className="absolute top-2 right-2 p-1.5 bg-red-500 text-white rounded-full hover:bg-red-600 transition-colors shadow-lg"
                      >
                        <X size={16} />
                      </button>
                    </div>
                  )}

                  <input
                    ref={fileInputRef}
                    type="file"
                    accept="image/*"
                    onChange={handleImageChange}
                    className="hidden"
                  />
                </div>

                <div className="pt-4 flex justify-end">
                  <Button
                    variation="primary"
                    type="submit"
                    disabled={isSubmitting}
                    className="w-full md:w-auto flex justify-center items-center gap-2 px-8"
                  >
                    {isSubmitting ? (
                      <>
                        <span className="animate-spin">⏳</span>
                        Submitting...
                      </>
                    ) : (
                      <>
                        <Send size={18} />
                        Submit Report
                      </>
                    )}
                  </Button>
                </div>
              </form>
            </div>
          </Card>

          {/* Report History */}
          <Card className="p-0 overflow-hidden shadow-lg">
            <div className="bg-gradient-to-r from-slate-700 to-slate-800 px-6 py-4">
              <h3 className="text-white font-semibold text-lg">
                Your Report History
              </h3>
            </div>
            <div className="bg-white p-6">
              {isReportsLoading ? (
                <p className="text-center text-gray-500 py-8">
                  Loading reports...
                </p>
              ) : reports?.length === 0 ? (
                <p className="text-center text-gray-500 py-8">
                  No reports submitted yet.
                </p>
              ) : (
                <div className="space-y-4">
                  {reports?.map((report) => (
                    <div
                      key={report.id}
                      className="flex items-center justify-between p-4 bg-gray-50 rounded-lg border border-gray-100 hover:shadow-md transition-shadow"
                    >
                      <div className="flex items-center gap-4">
                        <div className="p-3 bg-white rounded-lg shadow-sm">
                          <CreditCard size={24} className="text-gray-600" />
                        </div>
                        <div>
                          <p className="font-medium text-gray-900">
                            {report.cardType === "ID_CARD"
                              ? "Student ID Card"
                              : "Meal Card"}
                          </p>
                          <p className="text-sm text-gray-500">
                            Report #{report.id}
                          </p>
                        </div>
                      </div>
                      <div className="flex items-center gap-4">
                        <StatusBadge status={report.status} />
                        {report.proofDocUrl && (
                          <a
                            href={`http://localhost:8080${report.proofDocUrl}`}
                            target="_blank"
                            rel="noopener noreferrer"
                            className="text-blue-600 hover:text-blue-800 text-sm font-medium"
                          >
                            View Proof
                          </a>
                        )}
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </Card>
        </div>
      </div>
    </div>
  );
}

// Simple Icon component for local use
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
