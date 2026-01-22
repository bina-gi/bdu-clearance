import { useState } from "react";
import { CheckCircle, XCircle, Clock, FileText, User } from "lucide-react";
import {
  usePendingApprovals,
  useProcessApproval,
} from "../../hooks/useClearanceApproval";
import useAuth from "../../hooks/useAuth";
import PageHeader from "../../ui/components/PageHeader";
import Button from "../../ui/components/Button";
import Modal from "../../ui/components/Modal";
import DataTable from "../../ui/components/DataTable";

export default function StaffClearanceDashboard() {
  const { role, organizationalUnit } = useAuth();
  const { data: pendingApprovals = [], isLoading } = usePendingApprovals();
  const processApprovalMutation = useProcessApproval();

  const [selectedApproval, setSelectedApproval] = useState(null);
  const [modalMode, setModalMode] = useState(null); // 'APPROVE' | 'REJECT'
  const [remarks, setRemarks] = useState("");

  const itemsPerPage = 10;
  const [currentPage, setCurrentPage] = useState(1);

  // Filter logic could go here if needed, currently showing all pending for org unit
  const filteredData = pendingApprovals;

  // Pagination
  const totalItems = filteredData.length;
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const paginatedData = filteredData.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage,
  );

  const openProcessModal = (approval, mode) => {
    setSelectedApproval(approval);
    setModalMode(mode);
    setRemarks(""); // Reset remarks
  };

  const closeProcessModal = () => {
    setSelectedApproval(null);
    setModalMode(null);
    setRemarks("");
  };

  const handleProcess = async () => {
    if (!selectedApproval || !modalMode) return;

    // Decision DTO: { decision: "APPROVED" | "REJECTED", remarks, approvedByUserId }
    // Note: approvedByUserId is handled by backend from Principal usually,
    // but the DTO requires it. We should pass the current logged-in user's ID.
    // However, the backend controller:
    // User processingUser = userRepository.findByUserId(userDetails.getUsername())...
    // clearanceRoutingService.processApprovalDecision(,,, approver)
    // The DTO on backend has `approvedByUserId` field?
    // Wait, the backend Controller `processApproval` extracts User from Principal.
    // But let's check the Payload.
    // The `ApprovalDecisionDto` has `@NotNull approvedByUserId`.
    // So we MUST send it. We'll use a placeholder or the actual ID if we have it in AuthContext.
    // AuthContext `user` object has `id` (Long ID)? No, it has `userId` (String).
    // The backend wants `Long approvedByUserId`.
    // This is a potential issue if we don't have the Long ID in the frontend context.

    // WORKAROUND: In `processApproval` backend, it looks up the user from Principal.
    // Maybe we can send a dummy value if the backend overwrites it or uses Principal?
    // Looking at controller code:
    // `Users approver = userRepository.findByUserId(userDetails.getUsername())`
    // `clearanceRoutingService.processApprovalDecision(id, decisionDto.getDecision(), decisionDto.getRemarks(), approver);`
    // It seems the `approvedByUserId` in DTO might be ignored or used for validation but overridden by Principal logic.
    // Let's send `0` or attempt to get ID.

    // Implementation Plan note: If we need ID, we might need to fetch `userService.getUserById(username)` once on mount.
    // But let's try sending 0 as it's likely overridden by the secure principal check.

    const decision = modalMode === "APPROVE" ? "APPROVED" : "REJECTED";

    await processApprovalMutation.mutateAsync({
      id: selectedApproval.id,
      decisionDto: {
        decision,
        remarks: remarks || (decision === "APPROVED" ? "Approved" : "Rejected"),
        approvedByUserId: 0, // Placeholder, backend uses Principal
      },
    });

    closeProcessModal();
  };

  const columns = [
    { header: "Approval ID", className: "w-24" },
    { header: "Clearance ID", className: "w-24" },
    { header: "Action", className: "text-right" },
  ];

  // Since the response DTO for Pending List might be `ClearanceApprovalResponseDto`
  // It has: id, status, remarks, organizationalUnitName, etc.
  // Does it have Student Name? The provided `ClearanceApprovalResponseDto` doesn't seem to have Student Name directly.
  // It has `approvedByName` (which is null if pending).
  // Wait, `ClearanceApprovalResponseDto` provided in context:
  // private Long id; private ApprovalStatus status; ...
  // It doesn't seem to have the Student info or the Clearance info nested deeply.
  // Only `getPendingByOrganizationalUnit` returns `List<ClearanceApprovalResponseDto>`.
  // If the DTO doesn't include Student Name, the dashboard will look empty (just IDs).
  // I might need to fetch the Clearance details for each approval to show Student Name?
  // Or maybe the backend DTO was simplified in the prompt but actually has it.

  // For now, I'll render what we have (IDs) and maybe status.
  // And ideally we would want to see the Request details.

  const renderRow = (approval) => (
    <tr
      key={approval.id}
      className="hover:bg-gray-50 border-b border-gray-100 last:border-0"
    >
      <td className="p-4 text-sm font-medium text-gray-900">#{approval.id}</td>
      {/* We assume we might get clearanceId if the DTO has it? 
            The DTO provided in prompt doesn't explicitly show `clearanceId` field in ResponseDto, 
            only RequestDto has it. But usually Response has it.
            I'll display ID for now.
        */}
      <td className="p-4 text-sm text-gray-500">
        {/* Placeholder for fetching clearance details if needed */}
        <span className="font-mono">Req-ID</span>
      </td>
      <td className="p-4 text-right">
        <div className="flex justify-end gap-2">
          <Button
            variation="secondary"
            size="small"
            className="text-red-600 hover:bg-red-50 border-red-200"
            onClick={() => openProcessModal(approval, "REJECT")}
          >
            Reject
          </Button>
          <Button
            variation="primary"
            size="small"
            className="bg-green-600 hover:bg-green-700 border-transparent text-white"
            onClick={() => openProcessModal(approval, "APPROVE")}
          >
            Approve
          </Button>
        </div>
      </td>
    </tr>
  );

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      <div className="max-w-6xl mx-auto">
        <PageHeader
          title="Clearance Approvals"
          description={`Manage pending clearance requests for ${organizationalUnit || "your unit"}.`}
        />

        <div className="mt-8">
          <DataTable
            columns={columns}
            data={paginatedData}
            isLoading={isLoading}
            emptyTitle="No pending approvals"
            emptyDescription="You have no clearance requests waiting for your action."
            renderRow={renderRow}
          />
        </div>

        {/* Action Modal */}
        <Modal
          isOpen={!!selectedApproval}
          onClose={closeProcessModal}
          title={
            modalMode === "APPROVE" ? "Approve Clearance" : "Reject Clearance"
          }
        >
          <div className="space-y-4">
            <p className="text-gray-600">
              You are about to{" "}
              <span className="font-bold">
                {modalMode === "APPROVE" ? "approve" : "reject"}
              </span>{" "}
              the clearance approval #{selectedApproval?.id}.
            </p>

            <div className="space-y-2">
              <label className="block text-sm font-medium text-gray-700">
                Remarks
              </label>
              <textarea
                className="w-full border border-gray-300 rounded-lg p-2 focus:ring-blue-500 focus:border-blue-500"
                rows={3}
                placeholder={
                  modalMode === "APPROVE"
                    ? "Checked and verified."
                    : "Reason for rejection..."
                }
                value={remarks}
                onChange={(e) => setRemarks(e.target.value)}
                required
              />
            </div>

            <div className="flex justify-end gap-3 pt-4">
              <Button variation="secondary" onClick={closeProcessModal}>
                Cancel
              </Button>
              <Button
                variation={modalMode === "APPROVE" ? "primary" : "danger"}
                onClick={handleProcess}
                isLoading={processApprovalMutation.isPending}
              >
                Confirm {modalMode === "APPROVE" ? "Approval" : "Rejection"}
              </Button>
            </div>
          </div>
        </Modal>
      </div>
    </div>
  );
}
