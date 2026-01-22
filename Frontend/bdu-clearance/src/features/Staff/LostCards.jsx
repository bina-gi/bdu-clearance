import { useState } from "react";
import {
  CreditCard,
  CheckCircle,
  XCircle,
  Clock,
  AlertCircle,
  Eye,
  Search,
} from "lucide-react";
import {
  useAllLostCardReports,
  useProcessLostCardReport,
} from "../../hooks/useLostCardReport";
import useAuth from "../../hooks/useAuth";
import PageHeader from "../../ui/components/PageHeader";
import Button from "../../ui/components/Button";
import Modal from "../../ui/components/Modal";
import Card from "../../ui/components/Card";

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

export default function LostCards() {
  const { user } = useAuth();
  const { data: reports = [], isLoading } = useAllLostCardReports();
  const processReportMutation = useProcessLostCardReport();

  const [selectedReport, setSelectedReport] = useState(null);
  const [modalMode, setModalMode] = useState(null); // 'APPROVE' | 'REJECT' | 'VIEW'
  const [statusFilter, setStatusFilter] = useState("ALL");
  const [searchTerm, setSearchTerm] = useState("");

  // Filter reports
  const filteredReports = reports.filter((report) => {
    // Status filter
    if (statusFilter !== "ALL" && report.status !== statusFilter) {
      return false;
    }
    // Search filter (by student name or ID)
    if (searchTerm) {
      const searchLower = searchTerm.toLowerCase();
      const matchesStudent =
        report.studentName?.toLowerCase().includes(searchLower) ||
        String(report.studentId).includes(searchTerm);
      if (!matchesStudent) return false;
    }
    return true;
  });

  const openModal = (report, mode) => {
    setSelectedReport(report);
    setModalMode(mode);
  };

  const closeModal = () => {
    setSelectedReport(null);
    setModalMode(null);
  };

  const handleProcess = async (status) => {
    if (!selectedReport) return;

    await processReportMutation.mutateAsync({
      id: selectedReport.id,
      status,
      processedByUserId: user?.id || 0, // Using logged-in user's ID
    });

    closeModal();
  };

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      <div className="max-w-6xl mx-auto">
        <PageHeader
          title="Lost Card Reports"
          description="Review and manage lost card reports from students."
        />

        {/* Filters */}
        <div className="mt-6 flex flex-col md:flex-row gap-4">
          {/* Search */}
          <div className="relative flex-1">
            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
              <Search size={18} className="text-gray-400" />
            </div>
            <input
              type="text"
              placeholder="Search by student name or ID..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="w-full pl-10 pr-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
            />
          </div>

          {/* Status Filter */}
          <select
            value={statusFilter}
            onChange={(e) => setStatusFilter(e.target.value)}
            className="px-4 py-2.5 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 bg-white"
          >
            <option value="ALL">All Status</option>
            <option value="PENDING">Pending</option>
            <option value="UNDER_REVIEW">Under Review</option>
            <option value="APPROVED">Approved</option>
            <option value="REJECTED">Rejected</option>
          </select>
        </div>

        {/* Reports List */}
        <div className="mt-6">
          <Card className="p-0 overflow-hidden shadow-lg">
            <div className="bg-gradient-to-r from-slate-700 to-slate-800 px-6 py-4 flex justify-between items-center">
              <h3 className="text-white font-semibold text-lg flex items-center gap-2">
                <CreditCard size={20} />
                Lost Card Reports
              </h3>
              <span className="text-white/80 text-sm">
                {filteredReports.length} report(s)
              </span>
            </div>

            {isLoading ? (
              <div className="p-8 text-center text-gray-500">
                Loading reports...
              </div>
            ) : filteredReports.length === 0 ? (
              <div className="p-8 text-center text-gray-500">
                No lost card reports found.
              </div>
            ) : (
              <div className="overflow-x-auto">
                <table className="w-full">
                  <thead className="bg-gray-50 border-b border-gray-200">
                    <tr>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        ID
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Student
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Card Type
                      </th>
                      <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Status
                      </th>
                      <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase tracking-wider">
                        Actions
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-100">
                    {filteredReports.map((report) => (
                      <tr
                        key={report.id}
                        className="hover:bg-gray-50 transition-colors"
                      >
                        <td className="px-6 py-4 text-sm font-medium text-gray-900">
                          #{report.id}
                        </td>
                        <td className="px-6 py-4">
                          <div className="text-sm font-medium text-gray-900">
                            {report.studentName || "N/A"}
                          </div>
                          <div className="text-xs text-gray-500">
                            ID: {report.studentId}
                          </div>
                        </td>
                        <td className="px-6 py-4 text-sm text-gray-700">
                          {report.cardType === "ID_CARD"
                            ? "Student ID Card"
                            : "Meal Card"}
                        </td>
                        <td className="px-6 py-4">
                          <StatusBadge status={report.status} />
                        </td>
                        <td className="px-6 py-4 text-right">
                          <div className="flex justify-end gap-2">
                            {/* View Proof Button */}
                            {report.proofDocUrl && (
                              <a
                                href={`http://localhost:8080${report.proofDocUrl}`}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="inline-flex items-center gap-1 px-3 py-1.5 text-xs font-medium text-blue-600 bg-blue-50 rounded-lg hover:bg-blue-100 transition-colors"
                              >
                                <Eye size={14} />
                                View Proof
                              </a>
                            )}

                            {/* Action Buttons (only for PENDING) */}
                            {report.status === "PENDING" && (
                              <>
                                <Button
                                  variation="secondary"
                                  size="small"
                                  className="text-red-600 hover:bg-red-50 border-red-200"
                                  onClick={() => openModal(report, "REJECT")}
                                >
                                  Reject
                                </Button>
                                <Button
                                  variation="primary"
                                  size="small"
                                  className="bg-green-600 hover:bg-green-700 border-transparent text-white"
                                  onClick={() => openModal(report, "APPROVE")}
                                >
                                  Approve
                                </Button>
                              </>
                            )}
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </Card>
        </div>

        {/* Process Modal */}
        <Modal
          isOpen={
            !!selectedReport &&
            (modalMode === "APPROVE" || modalMode === "REJECT")
          }
          onClose={closeModal}
          title={modalMode === "APPROVE" ? "Approve Report" : "Reject Report"}
        >
          <div className="space-y-4">
            <p className="text-gray-600">
              Are you sure you want to{" "}
              <span className="font-bold">
                {modalMode === "APPROVE" ? "approve" : "reject"}
              </span>{" "}
              the lost card report #{selectedReport?.id} for{" "}
              <span className="font-bold">
                {selectedReport?.studentName ||
                  `Student ${selectedReport?.studentId}`}
              </span>
              ?
            </p>

            <div className="bg-gray-50 rounded-lg p-4">
              <div className="grid grid-cols-2 gap-4 text-sm">
                <div>
                  <span className="text-gray-500">Card Type:</span>
                  <p className="font-medium">
                    {selectedReport?.cardType === "ID_CARD"
                      ? "Student ID Card"
                      : "Meal Card"}
                  </p>
                </div>
                {selectedReport?.proofDocUrl && (
                  <div>
                    <span className="text-gray-500">Proof Document:</span>
                    <a
                      href={`http://localhost:8080${selectedReport.proofDocUrl}`}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="block text-blue-600 hover:underline font-medium"
                    >
                      View Document
                    </a>
                  </div>
                )}
              </div>
            </div>

            <div className="flex justify-end gap-3 pt-4">
              <Button variation="secondary" onClick={closeModal}>
                Cancel
              </Button>
              <Button
                variation={modalMode === "APPROVE" ? "primary" : "danger"}
                onClick={() =>
                  handleProcess(
                    modalMode === "APPROVE" ? "APPROVED" : "REJECTED",
                  )
                }
                disabled={processReportMutation.isPending}
                className={
                  modalMode === "APPROVE"
                    ? "bg-green-600 hover:bg-green-700"
                    : ""
                }
              >
                {processReportMutation.isPending
                  ? "Processing..."
                  : `Confirm ${modalMode === "APPROVE" ? "Approval" : "Rejection"}`}
              </Button>
            </div>
          </div>
        </Modal>
      </div>
    </div>
  );
}
