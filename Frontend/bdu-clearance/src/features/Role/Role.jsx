import { useState } from "react";
import { Shield } from "lucide-react";
import toast from "react-hot-toast";
import { useRoles, useUpdateRole, useDeleteRole } from "../../hooks/useRole";
import PageHeader from "../../ui/components/PageHeader";
import SearchInput from "../../ui/components/SearchInput";
import Modal from "../../ui/components/Modal";
import ConfirmDialog from "../../ui/components/ConfirmDialog";
import EmptyState from "../../ui/components/EmptyState";
import LoadingSpinner from "../../ui/components/LoadingSpinner";
import DataListCard from "../../ui/components/DataListCard";
import RoleForm from "./RoleForm";

export default function Role() {
  const [searchQuery, setSearchQuery] = useState("");
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [selectedRole, setSelectedRole] = useState(null);

  // React Query hooks
  const { data: roles = [], isLoading } = useRoles();
  const updateRoleMutation = useUpdateRole();
  const deleteRoleMutation = useDeleteRole();

  // Filter roles by search
  const filteredRoles = roles.filter((role) =>
    role.roleName.toLowerCase().includes(searchQuery.toLowerCase()),
  );

  // Handlers
  const handleUpdateRole = async (data) => {
    try {
      await updateRoleMutation.mutateAsync({ id: selectedRole.id, data });
      toast.success("Role updated successfully");
      setIsEditModalOpen(false);
      setSelectedRole(null);
    } catch (error) {
      console.error("Failed to update role:", error);
      toast.error("Failed to update role");
    }
  };

  const handleDeleteRole = async () => {
    try {
      await deleteRoleMutation.mutateAsync(selectedRole.id);
      toast.success("Role deleted successfully");
      setIsDeleteDialogOpen(false);
      setSelectedRole(null);
    } catch (error) {
      console.error("Failed to delete role:", error);
      toast.error("Failed to delete role");
    }
  };

  const openEditModal = (role) => {
    setSelectedRole(role);
    setIsEditModalOpen(true);
  };

  const openDeleteDialog = (role) => {
    setSelectedRole(role);
    setIsDeleteDialogOpen(true);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      {/* Header */}
      <div className="max-w-4xl mx-auto mb-8 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <PageHeader
          title="Role Management"
          description="Manage system roles and permissions."
        />

        <div className="flex gap-2 w-full sm:w-auto">
          <SearchInput
            value={searchQuery}
            onChange={setSearchQuery}
            placeholder="Search roles..."
            className="grow sm:grow-0 sm:w-64"
          />
        </div>
      </div>

      {/* Content */}
      <div className="max-w-4xl mx-auto">
        {isLoading ? (
          <div className="flex justify-center py-12">
            <LoadingSpinner size="large" />
          </div>
        ) : filteredRoles.length === 0 ? (
          <div className="bg-white rounded-xl shadow-sm border border-gray-100">
            <EmptyState
              title="No roles found"
              description="There are no system roles configured matching your search."
            />
          </div>
        ) : (
          <div className="flex flex-col gap-4">
            {filteredRoles.map((role) => (
              <DataListCard
                key={role.id}
                title={role.roleName}
                icon={Shield}
                onEdit={() => openEditModal(role)}
                onDelete={() => openDeleteDialog(role)}
                editLabel="Edit Role"
                deleteLabel="Delete Role"
              />
            ))}
          </div>
        )}
      </div>

      {/* Edit Modal */}
      <Modal
        isOpen={isEditModalOpen}
        onClose={() => {
          setIsEditModalOpen(false);
          setSelectedRole(null);
        }}
        title="Edit Role"
      >
        <RoleForm
          role={selectedRole}
          onSubmit={handleUpdateRole}
          onCancel={() => {
            setIsEditModalOpen(false);
            setSelectedRole(null);
          }}
          isLoading={updateRoleMutation.isPending}
        />
      </Modal>

      {/* Delete Confirmation */}
      <ConfirmDialog
        isOpen={isDeleteDialogOpen}
        onClose={() => {
          setIsDeleteDialogOpen(false);
          setSelectedRole(null);
        }}
        onConfirm={handleDeleteRole}
        title="Delete Role"
        message={`Are you sure you want to delete the role "${selectedRole?.roleName}"? This action cannot be undone.`}
        confirmText="Delete"
        isLoading={deleteRoleMutation.isPending}
      />
    </div>
  );
}
