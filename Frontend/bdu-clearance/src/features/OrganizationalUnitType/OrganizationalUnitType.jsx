import { useState } from "react";
import { Plus, Building2 } from "lucide-react";
import toast from "react-hot-toast";
import {
  useOrganizationalUnitTypes,
  useCreateOrganizationalUnitType,
  useUpdateOrganizationalUnitType,
  useDeleteOrganizationalUnitType,
} from "../../hooks/useOrganizationalUnitType";
import PageHeader from "../../ui/components/PageHeader";
import SearchInput from "../../ui/components/SearchInput";
import Modal from "../../ui/components/Modal";
import ConfirmDialog from "../../ui/components/ConfirmDialog";
import EmptyState from "../../ui/components/EmptyState";
import LoadingSpinner from "../../ui/components/LoadingSpinner";
import Button from "../../ui/components/Button";
import DataListCard from "../../ui/components/DataListCard";
import OrganizationalUnitTypeForm from "./OrganizationalUnitTypeForm";

export default function OrganizationalUnitType() {
  const [searchQuery, setSearchQuery] = useState("");
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);

  // React Query hooks
  const { data: orgTypes = [], isLoading } = useOrganizationalUnitTypes();
  const createMutation = useCreateOrganizationalUnitType();
  const updateMutation = useUpdateOrganizationalUnitType();
  const deleteMutation = useDeleteOrganizationalUnitType();

  // Filter items by search
  const filteredItems = orgTypes.filter((item) =>
    item.organizationType.toLowerCase().includes(searchQuery.toLowerCase()),
  );

  // Handlers
  const handleCreate = async (data) => {
    try {
      await createMutation.mutateAsync(data);
      toast.success("Organization type created successfully");
      setIsCreateModalOpen(false);
    } catch (error) {
      console.error("Failed to create type:", error);
      toast.error("Failed to create organization type");
    }
  };

  const handleUpdate = async (data) => {
    try {
      await updateMutation.mutateAsync({ id: selectedItem.id, data });
      toast.success("Organization type updated successfully");
      setIsEditModalOpen(false);
      setSelectedItem(null);
    } catch (error) {
      console.error("Failed to update type:", error);
      toast.error("Failed to update organization type");
    }
  };

  const handleDelete = async () => {
    try {
      await deleteMutation.mutateAsync(selectedItem.id);
      toast.success("Organization type deleted successfully");
      setIsDeleteDialogOpen(false);
      setSelectedItem(null);
    } catch (error) {
      console.error("Failed to delete type:", error);
      toast.error("Failed to delete organization type");
    }
  };

  const openEditModal = (item) => {
    setSelectedItem(item);
    setIsEditModalOpen(true);
  };

  const openDeleteDialog = (item) => {
    setSelectedItem(item);
    setIsDeleteDialogOpen(true);
  };

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      {/* Header */}
      <div className="max-w-5xl mx-auto mb-8 flex flex-col sm:flex-row justify-between items-center gap-4">
        <PageHeader
          title="Organization Types"
          description="Manage types of organizational units."
        />

        <div className="flex gap-2 w-full sm:w-auto">
          <SearchInput
            value={searchQuery}
            onChange={setSearchQuery}
            placeholder="Search types..."
            className="grow sm:grow-0 sm:w-64"
          />
          <Button
            variation="primary"
            size="small"
            onClick={() => setIsCreateModalOpen(true)}
            className="flex items-center gap-1 whitespace-nowrap "
          >
            <Plus size={16} />
            Add Type
          </Button>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-4xl mx-auto">
        {isLoading ? (
          <div className="flex justify-center py-12">
            <LoadingSpinner size="large" />
          </div>
        ) : filteredItems.length === 0 ? (
          <div className="bg-white rounded-xl shadow-sm border border-gray-100">
            <EmptyState
              title="No types found"
              description="There are no organization types configured."
            />
          </div>
        ) : (
          <div className="flex flex-col gap-4">
            {filteredItems.map((item) => (
              <DataListCard
                key={item.id}
                title={item.organizationType}
                icon={Building2}
                onEdit={() => openEditModal(item)}
                onDelete={() => openDeleteDialog(item)}
                editLabel="Edit Type"
                deleteLabel="Delete Type"
              />
            ))}
          </div>
        )}
      </div>

      {/* Create Modal */}
      <Modal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        title="Create Organization Type"
      >
        <OrganizationalUnitTypeForm
          onSubmit={handleCreate}
          onCancel={() => setIsCreateModalOpen(false)}
          isLoading={createMutation.isPending}
        />
      </Modal>

      {/* Edit Modal */}
      <Modal
        isOpen={isEditModalOpen}
        onClose={() => {
          setIsEditModalOpen(false);
          setSelectedItem(null);
        }}
        title="Edit Organization Type"
      >
        <OrganizationalUnitTypeForm
          initialData={selectedItem}
          onSubmit={handleUpdate}
          onCancel={() => {
            setIsEditModalOpen(false);
            setSelectedItem(null);
          }}
          isLoading={updateMutation.isPending}
        />
      </Modal>

      {/* Delete Confirmation */}
      <ConfirmDialog
        isOpen={isDeleteDialogOpen}
        onClose={() => {
          setIsDeleteDialogOpen(false);
          setSelectedItem(null);
        }}
        onConfirm={handleDelete}
        title="Delete Organization Type"
        message={`Are you sure you want to delete "${selectedItem?.organizationType}"? This action cannot be undone.`}
        confirmText="Delete"
        isLoading={deleteMutation.isPending}
      />
    </div>
  );
}
