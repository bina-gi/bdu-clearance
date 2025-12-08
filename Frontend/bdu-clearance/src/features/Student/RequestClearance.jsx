import React, { useState } from "react";
import Button from "../../ui/components/Button";

// --- Icon JSX Components (Simulating Lucide-React imports for self-containment) ---

const SvgIcon = ({ className, color, path, ...props }) => (
  <svg
    xmlns="http://www.w3.org/2000/svg"
    width="24"
    height="24"
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    strokeWidth="2"
    strokeLinecap="round"
    strokeLinejoin="round"
    className={`${className} ${color}`}
    {...props}
  >
    <path d={path} />
  </svg>
);

const GraduationCap = (props) => (
  <SvgIcon
    path="M21.4 11.2a1 1 0 0 0-.6-.4h-3.3c.3-.4.6-.7.9-1.2.7-1 1-2.2 1-3.6 0-3.3-2.7-6-6-6s-6 2.7-6 6c0 1.4.3 2.6 1 3.6.3.5.6.8.9 1.2H3.2a1 1 0 0 0-.6.4L1 15v.9c0 1.7 1.3 3 3 3h16c1.7 0 3-1.3 3-3V15l-1.6-3.8zM12 6c1.7 0 3 1.3 3 3s-1.3 3-3 3-3-1.3-3-3 1.3-3 3-3z"
    color="text-indigo-600"
    {...props}
  />
);

const CalendarRange = (props) => (
  <SvgIcon
    path="M8 2v4M16 2v4M21 7H3M3 10V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2v5M3 10h18M3 14h18M3 18h18M3 22h18"
    color="text-indigo-700"
    {...props}
  />
);

const Library = (props) => (
  <SvgIcon
    path="M6 21.3V5a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v16.3M12 21.3V3M2 21.3h20"
    color="text-yellow-600"
    {...props}
  />
);

const ForkAndKnife = (props) => (
  <SvgIcon
    path="M18 2h-3a4 4 0 0 0-4 4v7h2c2.8 0 4 1.2 4 4v5H18V2zM6 2v13h2V2H6z"
    color="text-green-600"
    {...props}
  />
);

const Bed = (props) => (
  <SvgIcon
    path="M2 7v10h20V7M2 10h20M6 10v4M18 10v4"
    color="text-red-600"
    {...props}
  />
);

const UserCheck = (props) => (
  <SvgIcon
    path="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2m8-11a4 4 0 1 0 0-8 4 4 0 0 0 0 8zm15-2l-5 5-2-2"
    color="text-indigo-600"
    {...props}
  />
);

const Send = (props) => (
  <SvgIcon
    path="M22 2L11 13M22 2l-7 20-4-9-9-4 20-7z"
    color="text-white"
    {...props}
  />
);

// ------------------------------------------------------------------------

export default function RequestClearance() {
  // State for academic inputs, updated to include 'otherReasonText'
  const [academicDetails, setAcademicDetails] = useState({
    reason: "",
    otherReasonText: "", // New state for custom reason input
    year: "",
    semester: "",
  });

  // State for clearance checklist
  const [clearanceStatus, setClearanceStatus] = useState({
    library: false,
    cafeteria: false,
    dormitory: false,
    registrar: false,
  });

  // State for managing on-screen notifications instead of alerts
  const [notification, setNotification] = useState({ message: "", type: "" });

  const reasonOptions = [
    { value: "graduation", label: "Final Graduation" },
    { value: "transfer", label: "Transfer to another institution" },
    { value: "withdrawal", label: "Temporary Withdrawal" },
    { value: "exchange", label: "Study Abroad/Exchange Program" },
    { value: "other", label: "Other (Please Specify)" },
  ];

  // Handle input changes for academic details (radio, text, and conditional input)
  const handleInputChange = (e) => {
    const { name, value } = e.target;

    if (name === "reason" && value !== "other") {
      // If user selects a non-'other' radio option, clear the custom text field
      setAcademicDetails((prev) => ({
        ...prev,
        reason: value,
        otherReasonText: "", // Reset custom text
      }));
    } else {
      setAcademicDetails((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
    setNotification({ message: "", type: "" }); // Clear notification on input
  };

  // Handle checkbox changes for clearance status
  const handleCheckChange = (e) => {
    const { name, checked } = e.target;
    setClearanceStatus((prev) => ({
      ...prev,
      [name]: checked,
    }));
    setNotification({ message: "", type: "" }); // Clear notification on input
  };

  // Handle form submission
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("--- Clearance Request Submitted ---");
    console.log("Academic Details:", academicDetails);
    console.log("Clearance Status:", clearanceStatus);

    const isFullyCleared = Object.values(clearanceStatus).every(
      (status) => status === true
    );

    const isReasonValid =
      academicDetails.reason === "other"
        ? academicDetails.otherReasonText.trim() !== ""
        : academicDetails.reason.trim() !== "";

    // Check if all fields are valid
    if (
      isFullyCleared &&
      isReasonValid &&
      academicDetails.year.trim() !== "" &&
      academicDetails.semester.trim() !== ""
    ) {
      setNotification({
        message:
          "Clearance request submitted successfully! Your submission will be reviewed by the Registrar's Office.",
        type: "success",
      });
      // Optionally reset form here
      // setAcademicDetails({ reason: '', otherReasonText: '', year: '', semester: '' });
      // setClearanceStatus({ library: false, cafeteria: false, dormitory: false, registrar: false });
    } else {
      setNotification({
        message:
          "Submission failed. Please fill out all required fields and check all clearance acknowledgments.",
        type: "error",
      });
    }
  };

  // List of clearance items for easy mapping
  const clearanceItems = [
    { name: "library", label: "Library", Component: Library, color: "yellow" },
    {
      name: "cafeteria",
      label: "Cafeteria",
      Component: ForkAndKnife,
      color: "green",
    },
    { name: "dormitory", label: "Dormitory", Component: Bed, color: "red" },
    {
      name: "registrar",
      label: "Registrar's Office",
      Component: UserCheck,
      color: "indigo",
    },
  ];

  // Check if the submit button should be enabled
  const isReasonValid =
    academicDetails.reason === "other"
      ? academicDetails.otherReasonText.trim() !== ""
      : academicDetails.reason.trim() !== "";

  const isSubmitEnabled =
    Object.values(clearanceStatus).every((status) => status === true) &&
    isReasonValid &&
    academicDetails.year.trim() !== "" &&
    academicDetails.semester.trim() !== "";

  // Notification style based on type
  const getNotificationClasses = (type) => {
    if (type === "success") {
      return "bg-green-100 border-green-400 text-green-700";
    }
    if (type === "error") {
      return "bg-red-100 border-red-400 text-red-700";
    }
    return "";
  };

  return (
    <div className="bg-gray-100  p-4 sm:p-8 font-sans">
      <div className="max-w-4xl mx-auto bg-white shadow-2xl rounded-xl p-6 sm:p-10">
        {/* Header */}
        <header className="mb-8 border-b pb-4">
          <div className="flex items-center space-x-3">
            {/* Using the new JSX component */}
            <GraduationCap className="w-8 h-8" />
            <h1 className="text-3xl font-extrabold text-gray-800">
              Student Clearance Request Form
            </h1>
          </div>
          <p className="text-gray-500 mt-2">
            Please provide your academic details and ensure all required units
            below have been cleared before submission.
          </p>
        </header>

        {/* Notification Area */}
        {notification.message && (
          <div
            className={`p-4 mb-6 border-l-4 rounded-lg ${getNotificationClasses(notification.type)}`}
            role="alert"
          >
            <p className="font-bold">
              {notification.type === "success" ? "Success" : "Error"}
            </p>
            <p>{notification.message}</p>
          </div>
        )}

        <form onSubmit={handleSubmit}>
          {/* 1. Academic Details Section */}
          <section className="mb-8 p-6 bg-indigo-50 rounded-lg border border-indigo-200">
            <h2 className="text-xl font-semibold text-indigo-700 mb-4 flex items-center">
              {/* Using the new JSX component */}
              <CalendarRange className="w-5 h-5 mr-2" />
              Academic Information
            </h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
              {/* Reason for Clearance Field (Radio Group) */}
              <div className="md:col-span-3">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Reason for Clearance
                </label>
                <div className="flex flex-wrap gap-4 p-3 bg-white rounded-lg border border-gray-300">
                  {reasonOptions.map((option) => (
                    <div key={option.value} className="flex items-center">
                      <input
                        type="radio"
                        id={`reason-${option.value}`}
                        name="reason"
                        value={option.value}
                        checked={academicDetails.reason === option.value}
                        onChange={handleInputChange}
                        className="w-4 h-4 text-indigo-600 border-gray-300 focus:ring-indigo-500 transition"
                        required={!academicDetails.reason}
                      />
                      <label
                        htmlFor={`reason-${option.value}`}
                        className="ml-2 text-sm text-gray-700 cursor-pointer"
                      >
                        {option.label}
                      </label>
                    </div>
                  ))}
                </div>
              </div>

              {/* Conditional Other Reason Input */}
              {academicDetails.reason === "other" && (
                <div className="md:col-span-3 mt-2">
                  <label
                    htmlFor="otherReasonText"
                    className="block text-sm font-medium text-gray-700 mb-1"
                  >
                    Please Specify Your Reason
                  </label>
                  <input
                    type="text"
                    id="otherReasonText"
                    name="otherReasonText"
                    placeholder="Enter your specific reason..."
                    value={academicDetails.otherReasonText}
                    onChange={handleInputChange}
                    className="w-full p-2 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition"
                    required
                  />
                </div>
              )}

              {/* Year of Study Field */}
              <div className="md:col-span-1">
                <label
                  htmlFor="year"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Year of Study
                </label>
                <input
                  type="text"
                  id="year"
                  name="year"
                  placeholder="e.g., 4"
                  value={academicDetails.year}
                  onChange={handleInputChange}
                  className="w-full p-2 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition"
                  required
                />
              </div>

              {/* Semester Field (Updated label) */}
              <div className="md:col-span-1">
                <label
                  htmlFor="semester"
                  className="block text-sm font-medium text-gray-700 mb-1"
                >
                  Undergraduate / Semester
                </label>
                <input
                  type="text"
                  id="semester"
                  name="semester"
                  placeholder="e.g., 2"
                  value={academicDetails.semester}
                  onChange={handleInputChange}
                  className="w-full p-2 border border-gray-300 rounded-lg focus:ring-indigo-500 focus:border-indigo-500 transition"
                  required
                />
              </div>
            </div>
          </section>

          {/* 2. Clearance Units Checklist Section */}
          <section className="mb-8">
            <h2 className="text-2xl font-bold text-gray-800 mb-4 border-b pb-2">
              Required Clearance Units
            </h2>

            <div className="space-y-4">
              {clearanceItems.map((item) => {
                const IconComponent = item.Component; // Get the specific Icon JSX component
                return (
                  <div
                    key={item.name}
                    className={`flex items-center justify-between p-4 bg-white border rounded-lg shadow-sm transition duration-150 ${
                      clearanceStatus[item.name]
                        ? "border-green-400 ring-1 ring-green-200"
                        : "border-gray-200 hover:shadow-md"
                    }`}
                  >
                    <div className="flex items-center space-x-3">
                      {/* Using the Icon JSX component */}
                      <IconComponent className="w-6 h-6" />
                      <span className="text-lg font-medium text-gray-700">
                        {item.label}
                      </span>
                    </div>
                    <label
                      htmlFor={`${item.name}-clearance`}
                      className="flex items-center cursor-pointer"
                    >
                      <span className="text-sm mr-3 text-gray-600 hidden sm:inline">
                        Acknowledge Clearance:
                      </span>
                      <input
                        type="checkbox"
                        id={`${item.name}-clearance`}
                        name={item.name}
                        checked={clearanceStatus[item.name]}
                        onChange={handleCheckChange}
                        className={`w-5 h-5 text-${item.color}-600 border-gray-300 rounded focus:ring-${item.color}-500 transition`}
                      />
                    </label>
                  </div>
                );
              })}
            </div>

            {!isSubmitEnabled && (
              <p className="mt-4 text-sm text-red-600 bg-red-50 p-3 rounded-lg flex items-center">
                <span className="font-semibold mr-1">Note:</span> Please select
                a reason (and specify if 'Other'), fill out your academic
                details, and check all four clearance acknowledgments to enable
                submission.
              </p>
            )}
          </section>

          {/* 3. Submit Button */}
          <div className="mt-10 pt-4 border-t flex justify-end">
            <button
              type="submit"
              disabled={!isSubmitEnabled}
              className={`px-8 py-3 font-semibold rounded-lg shadow-md transition duration-150 ease-in-out flex items-center
                                ${
                                  isSubmitEnabled
                                    ? "bg-indigo-600 text-white hover:bg-indigo-700 focus:ring-4 focus:ring-indigo-500 focus:ring-opacity-50"
                                    : "bg-gray-300 text-gray-500 cursor-not-allowed"
                                }`}
            >
              {/* Using the new JSX component */}
              <Send className="w-5 h-5 mr-2" />
              Submit Clearance Request
            </button>
            <Button size="small" variation="secondary">
              Small
            </Button>
            <Button>Medium</Button>
            <Button size="large" variation="danger">
              Large Danger
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
}
