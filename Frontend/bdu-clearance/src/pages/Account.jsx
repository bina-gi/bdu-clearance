import {
  LogOut,
  User,
  Shield,
  Building2,
  Briefcase,
  GraduationCap,
  Users,
} from "lucide-react";
import useAuth from "../hooks/useAuth";
import { useStudent } from "../hooks/useStudent";
import PageHeader from "../ui/components/PageHeader";
import Button from "../ui/components/Button";
import { useQuery } from "@tanstack/react-query";
import userService from "../services/userService";

function Account() {
  const { user, logout } = useAuth();

  // 1. Fetch Full User Profile
  // We rely on the API to get the most up-to-date and complete info (names, org unit, etc.)
  const { data: userProfile, isLoading: isUserLoading } = useQuery({
    queryKey: ["user", user?.username],
    queryFn: () => userService.getUserById(user.username),
    enabled: !!user?.username,
  });

  const role = userProfile?.roleName;
  const isStudent = role === "STUDENT";
  const fullName = userProfile
    ? `${userProfile.firstName} ${userProfile.middleName ? userProfile.middleName + " " : ""}${userProfile.lastName}`
    : "Loading...";
  const organizationalUnit = userProfile?.organizationalUnitName;

  // 2. Conditionally fetch student data if role is STUDENT
  const { data: studentData, isLoading: isStudentLoading } = useStudent(
    isStudent ? user.username : null,
  );

  const handleLogout = () => {
    logout();
  };

  if (!user) return null;

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      <div className="max-w-2xl mx-auto">
        <PageHeader
          title="My Account"
          description="View your profile details and manage your session."
        />

        <div className="bg-white rounded-2xl shadow-sm border border-gray-100 overflow-hidden mt-8">
          {/* Header / Banner */}
          <div className="bg-linear-to-r from-blue-600 to-blue-500 h-32 relative">
            <div className="absolute -bottom-10 left-8">
              <div className="w-20 h-20 bg-white rounded-full p-1 shadow-md">
                <div className="w-full h-full bg-blue-100 rounded-full flex items-center justify-center text-blue-600">
                  <User size={40} />
                </div>
              </div>
            </div>
          </div>

          <div className="pt-12 px-8 pb-8">
            <div className="mb-6">
              <h2 className="text-2xl font-bold text-gray-900">
                {isUserLoading ? "Loading..." : fullName}
              </h2>
              <p className="text-gray-500 text-sm">User ID: {user.username}</p>
            </div>

            <div className="grid gap-6 border-t border-gray-100 pt-6">
              {/* Role */}
              <div className="flex items-start gap-4">
                <div className="p-2 bg-purple-50 text-purple-600 rounded-lg">
                  <Shield size={20} />
                </div>
                <div>
                  <p className="text-sm font-medium text-gray-500 mb-1">Role</p>
                  <p className="font-semibold text-gray-900">
                    {role || "No Role Assigned"}
                  </p>
                </div>
              </div>

              {/* Organization Unit */}
              <div className="flex items-start gap-4">
                <div className="p-2 bg-amber-50 text-amber-600 rounded-lg">
                  <Building2 size={20} />
                </div>
                <div>
                  <p className="text-sm font-medium text-gray-500 mb-1">
                    Organizational Unit
                  </p>
                  <p className="font-semibold text-gray-900">
                    {organizationalUnit || "N/A"}
                  </p>
                </div>
              </div>

              {/* Account Status */}
              <div className="flex items-start gap-4">
                <div className="p-2 bg-green-50 text-green-600 rounded-lg">
                  <Briefcase size={20} />
                </div>
                <div>
                  <p className="text-sm font-medium text-gray-500 mb-1">
                    Account Status
                  </p>
                  <div className="flex items-center gap-2">
                    <div className="h-2.5 w-2.5 rounded-full bg-green-500"></div>
                    <p className="font-semibold text-gray-900">Active</p>
                  </div>
                </div>
              </div>

              {/* Student Details Section */}
              {isStudent && (
                <div className="mt-6 pt-6 border-t border-gray-100">
                  <h3 className="text-lg font-semibold text-gray-900 mb-4">
                    Student Profile
                  </h3>

                  {isStudentLoading ? (
                    <p className="text-gray-500 text-sm">
                      Loading student details...
                    </p>
                  ) : studentData ? (
                    <div className="grid gap-6">
                      {/* Year of Study */}
                      <div className="flex items-start gap-4">
                        <div className="p-2 bg-indigo-50 text-indigo-600 rounded-lg">
                          <GraduationCap size={20} />
                        </div>
                        <div>
                          <p className="text-sm font-medium text-gray-500 mb-1">
                            Year of Study
                          </p>
                          <p className="font-semibold text-gray-900">
                            Year {studentData.yearOfStudy}
                          </p>
                          <span
                            className={`text-xs px-2 py-0.5 rounded-full mt-1 inline-block ${
                              studentData.studentStatus === "ACTIVE"
                                ? "bg-green-100 text-green-800"
                                : studentData.studentStatus === "GRADUATED"
                                  ? "bg-blue-100 text-blue-800"
                                  : "bg-gray-100 text-gray-800"
                            }`}
                          >
                            {studentData.studentStatus}
                          </span>
                        </div>
                      </div>

                      {/* Advisor */}
                      <div className="flex items-start gap-4">
                        <div className="p-2 bg-pink-50 text-pink-600 rounded-lg">
                          <Users size={20} />
                        </div>
                        <div>
                          <p className="text-sm font-medium text-gray-500 mb-1">
                            Academic Advisor
                          </p>
                          <p className="font-semibold text-gray-900">
                            {studentData.advisorName || "Not Assigned"}
                          </p>
                        </div>
                      </div>
                    </div>
                  ) : (
                    <div className="p-4 bg-amber-50 text-amber-800 rounded-lg text-sm">
                      Student profile information could not be loaded. Please
                      contact support.
                    </div>
                  )}
                </div>
              )}
            </div>

            <div className="mt-10 pt-6 border-t border-gray-100 flex justify-end">
              <Button
                variation="danger"
                onClick={handleLogout}
                className="flex items-center gap-2"
              >
                <LogOut size={18} />
                Sign Out
              </Button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Account;
