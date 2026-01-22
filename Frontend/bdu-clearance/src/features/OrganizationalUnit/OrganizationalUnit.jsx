import { useState } from "react";
import { Plus, Pencil, Trash2 } from "lucide-react";
import {
  useOrganizationalUnits,
  useCreateOrganizationalUnit,
  useUpdateOrganizationalUnit,
  useDeleteOrganizationalUnit,
} from "../../hooks/useOrganizationalUnit";
import PageHeader from "../../ui/components/PageHeader";
import SearchInput from "../../ui/components/SearchInput";
import Modal from "../../ui/components/Modal";
import ConfirmDialog from "../../ui/components/ConfirmDialog";
import DataTable from "../../ui/components/DataTable";
import Button from "../../ui/components/Button";
import OrganizationalUnitForm from "./OrganizationalUnitForm";

export default function OrganizationalUnit() {
  const [searchQuery, setSearchQuery] = useState("");
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);

  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  // React Query hooks
  const { data: orgUnits = [], isLoading } = useOrganizationalUnits();
  const createMutation = useCreateOrganizationalUnit();
  const updateMutation = useUpdateOrganizationalUnit();
  const deleteMutation = useDeleteOrganizationalUnit();

  // Filter items by search
  const filteredItems = orgUnits.filter(
    (item) =>
      item.organizationName.toLowerCase().includes(searchQuery.toLowerCase()) ||
      item.organizationId.toLowerCase().includes(searchQuery.toLowerCase()),
  );

  // Client-side Pagination Logic
  const totalItems = filteredItems.length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const paginatedData = filteredItems.slice(
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
  const handleCreate = async (data) => {
    await createMutation.mutateAsync(data);
    setIsCreateModalOpen(false);
  };

  const handleUpdate = async (data) => {
    await updateMutation.mutateAsync({ id: selectedItem.id, data });
    setIsEditModalOpen(false);
    setSelectedItem(null);
  };

  const handleDelete = async () => {
    await deleteMutation.mutateAsync(selectedItem.id);
    setIsDeleteDialogOpen(false);
    setSelectedItem(null);
  };

  const openEditModal = (item) => {
    setSelectedItem(item);
    setIsEditModalOpen(true);
  };

  const openDeleteDialog = (item) => {
    setSelectedItem(item);
    setIsDeleteDialogOpen(true);
  };

  // Table Columns
  const columns = [
    { header: "Org ID", className: "w-32" },
    { header: "Name", className: "" },
    { header: "Type", className: "w-48" },
    { header: "Actions", className: "w-32 text-right" },
  ];

  const renderRow = (item) => (
    <tr
      key={item.id}
      className="hover:bg-gray-50 border-b border-gray-100 last:border-0"
    >
      <td className="p-4 text-sm font-medium text-gray-900">
        {item.organizationId}
      </td>
      <td className="p-4 text-sm text-gray-600 font-medium">
        {item.organizationName}
      </td>
      <td className="p-4 text-sm text-gray-500">
        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800">
          {item.organizationalUnitTypeName}
        </span>
      </td>
      <td className="p-4 text-right">
        <div className="flex justify-end gap-2">
          <button
            onClick={() => openEditModal(item)}
            className="p-1.5 text-blue-600 hover:bg-blue-50 rounded transition-colors"
            title="Edit"
          >
            <Pencil size={18} />
          </button>
          <button
            onClick={() => openDeleteDialog(item)}
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
          title="Organizational Units"
          description="Manage organizational units and their hierarchy."
        />

        <div className="flex gap-2 w-full sm:w-auto">
          <SearchInput
            value={searchQuery}
            onChange={(val) => {
              setSearchQuery(val);
              setCurrentPage(1); // Reset to page 1 on search
            }}
            placeholder="Search units..."
            className="grow sm:grow-0 sm:w-64"
          />
          <Button
            variation="primary"
            size="small"
            onClick={() => setIsCreateModalOpen(true)}
            className="flex items-center gap-1 whitespace-nowrap"
          >
            <Plus size={16} />
            Add Unit
          </Button>
        </div>
      </div>

      {/* Table */}
      <div className="max-w-6xl mx-auto">
        <DataTable
          columns={columns}
          data={paginatedData}
          isLoading={isLoading}
          emptyTitle="No organizational units found"
          emptyDescription="Create a new unit to get started."
          renderRow={renderRow}
          pagination={totalItems > 0 ? paginationProps : null}
        />
      </div>

      {/* Create Modal */}
      <Modal
        isOpen={isCreateModalOpen}
        onClose={() => setIsCreateModalOpen(false)}
        title="Create Organizational Unit"
      >
        <OrganizationalUnitForm
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
        title="Edit Organizational Unit"
      >
        <OrganizationalUnitForm
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
        title="Delete Unit"
        message={`Are you sure you want to delete "${selectedItem?.organizationName}"? This action cannot be undone.`}
        confirmText="Delete"
        isLoading={deleteMutation.isPending}
      />
    </div>
  );
}
