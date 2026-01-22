import { useState } from "react";
import { Plus, Pencil, Trash2, KeyRound } from "lucide-react";
import toast from "react-hot-toast";
import {
  useUsers,
  useCreateUser,
  useUpdateUser,
  useDeleteUser,
  useResetPassword,
} from "../../hooks/useUser";
import {
  useCreateStudent,
  useStudent,
  useUpdateStudent,
  useDeleteStudent,
} from "../../hooks/useStudent";
import { useRoles } from "../../hooks/useRole";
import PageHeader from "../../ui/components/PageHeader";
import SearchInput from "../../ui/components/SearchInput";
import Modal from "../../ui/components/Modal";
import ConfirmDialog from "../../ui/components/ConfirmDialog";
import DataTable from "../../ui/components/DataTable";
import Button from "../../ui/components/Button";
import UserForm from "./UserForm";
import StudentForm from "../Student/StudentForm";
import userService from "../../services/userService";

export default function UserManagement() {
  const [searchQuery, setSearchQuery] = useState("");
  const [isUserModalOpen, setIsUserModalOpen] = useState(false);
  const [isStudentModalOpen, setIsStudentModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

  // For Student Creation Sequence
  const [pendingStudentUser, setPendingStudentUser] = useState(null);

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  // React Query hooks
  const { data: users = [], isLoading } = useUsers();
  const { data: roles = [] } = useRoles(); // Need roles to check "STUDENT" role ID
  const createUserMutation = useCreateUser();
  const updateUserMutation = useUpdateUser();
  const deleteUserMutation = useDeleteUser();
  const resetPasswordMutation = useResetPassword();
  const createStudentMutation = useCreateStudent();
  const updateStudentMutation = useUpdateStudent();
  const deleteStudentMutation = useDeleteStudent();

  // Fetch student data only when editing a student
  const isStudentSelected =
    selectedUser?.roleName === "STUDENT" && isEditModalOpen;
  const { data: studentData, isLoading: isStudentDataLoading } = useStudent(
    isStudentSelected ? selectedUser.userId : null,
  );

  // Helper to find ID of "STUDENT" role
  const getStudentRoleId = () => {
    const studentRole = roles.find((r) => r.roleName === "STUDENT");
    return studentRole ? studentRole.id : null;
  };

  // Filter items by search
  const filteredUsers = users.filter((user) => {
    const search = searchQuery.toLowerCase();
    return (
      user.firstName.toLowerCase().includes(search) ||
      user.lastName.toLowerCase().includes(search) ||
      user.userId.toLowerCase().includes(search)
    );
  });

  // Client-side Pagination
  const totalItems = filteredUsers.length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const paginatedData = filteredUsers.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage,
  );

  const paginationProps = {
    from: (currentPage - 1) * itemsPerPage + 1,
    to: Math.min(currentPage * itemsPerPage, totalItems),
    total: totalItems,
    currentPage: currentPage,
    totalPages: totalPages,
    onPrev: () => setCurrentPage((p) => Math.max(1, p - 1)),
    onNext: () => setCurrentPage((p) => Math.min(totalPages, p + 1)),
  };

  // Handlers
  const handleCreateUser = async (data) => {
    try {
      // 1. Create User
      await createUserMutation.mutateAsync(data);
      setIsUserModalOpen(false);

      // 2. Check if created user is a STUDENT
      const studentRoleId = getStudentRoleId();
      if (studentRoleId && parseInt(data.roleId) === studentRoleId) {
        // Need to fetch the FULL user object to get the Long ID (database ID)
        // required for creating the student record
        try {
          // data.userId is the String ID (e.g., U12345)
          const fetchedUser = await userService.getUserById(data.userId);

          setPendingStudentUser(fetchedUser);
          setIsStudentModalOpen(true);
          toast("Please complete the student profile details", {
            icon: "ℹ️",
            duration: 5000,
          });
        } catch (err) {
          console.error("Failed to fetch new user details", err);
          toast.error(
            "User created, but failed to start student profile setup.",
          );
        }
      }
    } catch (error) {
      // Error handled by hook
    }
  };

  const handleCreateStudent = async (data) => {
    try {
      await createStudentMutation.mutateAsync(data);
      setIsStudentModalOpen(false);
      setPendingStudentUser(null);
    } catch (error) {
      // Error handled by hook
    }
  };

  const handleUpdate = async (data) => {
    await updateUserMutation.mutateAsync({ id: selectedUser.id, data });
    // Don't close modal here if we want to allow editing student details too?
    // Or just let them close it manually or close it.
    // If we only have UserForm submit, we close.
    // But now we have two forms.
    // If I update User, I might want to stay to update Student.
    // But usually "Update" button implies finishing.
    // Let's close it for now, as they are separate save buttons.
    setIsEditModalOpen(false);
    setSelectedUser(null);
  };

  const handleUpdateStudent = async (data) => {
    if (!studentData?.id) return;
    await updateStudentMutation.mutateAsync({ id: studentData.id, data });
    setIsEditModalOpen(false);
    setSelectedUser(null);
  };

  const handleDelete = async () => {
    try {
      // If user is a student, we try to delete the student profile first
      if (selectedUser.roleName === "STUDENT") {
        try {
          // Attempt to find student record by User ID
          // Note: We need to use the service directly or rely on the hook if we had the ID.
          // Since we don't have the student ID easily available without fetching,
          // we can try fetching it first.

          // However, using the service directly inside this handler avoids hook rules issues
          // and allows for imperative logic.
          // Assuming we can get student by user string ID or we need to try getting it.
          // studentService.getStudent requires studentId (string ID like U12345?? No, probably not)
          // Let's look at studentService.getStudent(studentId).
          // Wait, getStudent takes "studentId" which is likely the String ID of the STUDENT,
          // which is the same as the User string ID in many systems, OR we query by userId.

          // studentService.getStudent implementation:
          // const getStudent = async (studentId) => {
          //   const response = await api.get(`${BASE_URL}/${studentId}`);
          //   return response.data;
          // };

          // If the backend endpoint /public/students/{id} accepts the Student String ID,
          // and if Student String ID == User String ID (e.g. U12345), then we are good.
          // If not, we might fail to find it.

          // Let's assume we can try to delete using the User ID as the Student ID
          // if they share the same identifier, OR simply try to delete it.
          // Is there a delete endpoint that takes userId? No, deleteStudent takes studentId.

          // Safe approach: Try to fetch student details using the User ID (assuming getStudent supports it
          // or we have a way).
          // Actually, looking at previous code: `studentService.getStudent(data.userId)` was used
          // in `handleCreateUser` line 107 to fetch the USER. Wait, line 107 is `userService.getUserById`.

          // The `useStudent` hook uses `studentService.getStudent(studentId)`.
          // In `UserManagement`, line 56: `useStudent(isStudentSelected ? selectedUser.userId : null)`.
          // This implies `studentService.getStudent` accepts the `userId` (String ID U12345).

          // So we can proceed with deleting using the same ID.
          await deleteStudentMutation.mutateAsync(selectedUser.userId);
        } catch (err) {
          // If 404, maybe it didn't exist or was already deleted. We proceed to delete user.
          console.warn(
            "Could not delete student profile or it didn't exist",
            err,
          );
        }
      }

      await deleteUserMutation.mutateAsync(selectedUser.userId); // Delete takes String ID
      setIsDeleteDialogOpen(false);
      setSelectedUser(null);
      toast.success("User deleted successfully");
    } catch (error) {
      console.error("Failed to delete user", error);
    }
  };

  const handleResetPassword = async (user) => {
    if (confirm(`Reset password for ${user.firstName} ${user.lastName}?`)) {
      await resetPasswordMutation.mutateAsync(user.userId); // Reset takes String ID
    }
  };

  const openEditModal = (user) => {
    setSelectedUser(user);
    setIsEditModalOpen(true);
  };

  const openDeleteDialog = (user) => {
    setSelectedUser(user);
    setIsDeleteDialogOpen(true);
  };

  // Table Columns
  const columns = [
    { header: "User ID", className: "w-24" },
    { header: "Full Name", className: "" },
    { header: "Role", className: "w-32" },
    { header: "Org Unit", className: "w-48" },
    { header: "Status", className: "w-24" },
    { header: "Actions", className: "w-40 text-right" },
  ];

  const renderRow = (user) => (
    <tr
      key={user.id}
      className="hover:bg-gray-50 border-b border-gray-100 last:border-0"
    >
      <td className="p-4 text-sm font-medium text-gray-900">{user.userId}</td>
      <td className="p-4 text-sm text-gray-600 font-medium">
        {user.firstName} {user.middleName} {user.lastName}
      </td>
      <td className="p-4 text-sm text-gray-500">
        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
          {user.roleName}
        </span>
      </td>
      <td className="p-4 text-sm text-gray-500">
        {user.organizationalUnitName}
      </td>
      <td className="p-4">
        <span
          className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
            user.isActive
              ? "bg-green-100 text-green-800"
              : "bg-red-100 text-red-800"
          }`}
        >
          {user.isActive ? "Active" : "Inactive"}
        </span>
      </td>
      <td className="p-4 text-right">
        <div className="flex justify-end gap-2">
          <button
            onClick={() => handleResetPassword(user)}
            className="p-1.5 text-orange-500 hover:bg-orange-50 rounded transition-colors"
            title="Reset Password"
          >
            <KeyRound size={18} />
          </button>
          <button
            onClick={() => openEditModal(user)}
            className="p-1.5 text-blue-600 hover:bg-blue-50 rounded transition-colors"
            title="Edit"
          >
            <Pencil size={18} />
          </button>
          <button
            onClick={() => openDeleteDialog(user)}
            className="p-1.5 text-red-600 hover:bg-red-50 rounded transition-colors"
            title="Delete"
          >
            <Trash2 size={18} />
          </button>
        </div>
      </td>
    </tr>
  );

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      {/* Header */}
      <div className="max-w-6xl mx-auto mb-8 flex flex-col sm:flex-row justify-between items-center gap-4">
        <PageHeader
          title="User Management"
          description="Manage system users and access controls."
        />

        <div className="flex gap-2 w-full sm:w-auto">
          <SearchInput
            value={searchQuery}
            onChange={(val) => {
              setSearchQuery(val);
              setCurrentPage(1);
            }}
            placeholder="Search users..."
            className="grow sm:grow-0 sm:w-64"
          />
          <Button
            variation="primary"
            size="small"
            onClick={() => setIsUserModalOpen(true)}
            className="flex items-center gap-1 whitespace-nowrap"
          >
            <Plus size={16} />
            Add User
          </Button>
        </div>
      </div>

      {/* Table */}
      <div className="max-w-6xl mx-auto">
        <DataTable
          columns={columns}
          data={paginatedData}
          isLoading={isLoading}
          emptyTitle="No users found"
          emptyDescription="Start by adding a new user to the system."
          renderRow={renderRow}
          pagination={totalItems > 0 ? paginationProps : null}
        />
      </div>

      {/* Create User Modal */}
      <Modal
        isOpen={isUserModalOpen}
        onClose={() => setIsUserModalOpen(false)}
        title="Create New User"
      >
        <UserForm
          onSubmit={handleCreateUser}
          onCancel={() => setIsUserModalOpen(false)}
          isLoading={createUserMutation.isPending}
        />
      </Modal>

      {/* Create Student Modal (Chained) */}
      <Modal
        isOpen={isStudentModalOpen}
        onClose={() => {
          // Optional: Prevent closing without filling info?
          // For now allow close, maybe they fill it later (though no UI for it yet)
          setIsStudentModalOpen(false);
          setPendingStudentUser(null);
        }}
        title="Add Student Details"
      >
        {pendingStudentUser && (
          <StudentForm
            userId={pendingStudentUser.id}
            userStringId={pendingStudentUser.userId}
            userName={`${pendingStudentUser.firstName} ${pendingStudentUser.lastName}`}
            onSubmit={handleCreateStudent}
            onCancel={() => {
              setIsStudentModalOpen(false);
              setPendingStudentUser(null);
            }}
            isLoading={createStudentMutation.isPending}
          />
        )}
      </Modal>

      {/* Edit Modal */}
      <Modal
        isOpen={isEditModalOpen}
        onClose={() => {
          setIsEditModalOpen(false);
          setSelectedUser(null);
        }}
        title="Edit User"
      >
        <UserForm
          initialData={selectedUser}
          onSubmit={handleUpdate}
          onCancel={() => {
            setIsEditModalOpen(false);
            setSelectedUser(null);
          }}
          isLoading={updateUserMutation.isPending}
        />

        {/* Student Details Section */}
        {isStudentSelected && (
          <div className="mt-8 pt-6 border-t border-gray-100">
            <h3 className="text-lg font-medium text-gray-900 mb-4">
              Student Details
            </h3>
            {isStudentDataLoading ? (
              <div className="text-center py-4 text-gray-500">
                Loading student details...
              </div>
            ) : studentData ? (
              <StudentForm
                initialData={studentData}
                userId={studentData.userId} // Backend ID
                userStringId={selectedUser.userId}
                userName={`${selectedUser.firstName} ${selectedUser.lastName}`}
                onSubmit={handleUpdateStudent}
                onCancel={() => {
                  setIsEditModalOpen(false);
                  setSelectedUser(null);
                }}
                isLoading={updateStudentMutation.isPending}
              />
            ) : (
              <div className="text-amber-600 bg-amber-50 p-4 rounded-lg">
                Student profile not found. This user has the Student role but no
                linked student record.
              </div>
            )}
          </div>
        )}
      </Modal>

      {/* Delete Confirmation */}
      <ConfirmDialog
        isOpen={isDeleteDialogOpen}
        onClose={() => {
          setIsDeleteDialogOpen(false);
          setSelectedUser(null);
        }}
        onConfirm={handleDelete}
        title="Delete User"
        message={`Are you sure you want to delete "${selectedUser?.firstName} ${selectedUser?.lastName}" (${selectedUser?.userId})? This action cannot be undone.`}
        confirmText="Delete"
        isLoading={deleteUserMutation.isPending}
      />
    </div>
  );
}
