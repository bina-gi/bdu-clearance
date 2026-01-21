import LoadingSpinner from "./LoadingSpinner";
import EmptyState from "./EmptyState";

function DataTable({
  columns,
  data,
  isLoading = false,
  emptyTitle = "No data found",
  emptyDescription = "There are no items to display.",
  renderRow,
  pagination = null,
}) {
  if (isLoading) {
    return (
      <div className="bg-white rounded-xl shadow-sm border border-gray-200 p-8">
        <LoadingSpinner size="large" />
      </div>
    );
  }

  if (!data || data.length === 0) {
    return (
      <div className="bg-white rounded-xl shadow-sm border border-gray-200">
        <EmptyState title={emptyTitle} description={emptyDescription} />
      </div>
    );
  }

  return (
    <div className="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
      <div className="overflow-x-auto">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-gray-50 border-b border-gray-200 text-xs uppercase tracking-wider text-gray-500 font-semibold">
              {columns.map((column, index) => (
                <th key={index} className={`p-4 ${column.className || ""}`}>
                  {column.header}
                </th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {data.map((item, index) => renderRow(item, index))}
          </tbody>
        </table>
      </div>

      {pagination && (
        <div className="p-4 border-t border-gray-200 bg-gray-50 flex justify-between items-center text-xs text-gray-500">
          <span>
            Showing {pagination.from} - {pagination.to} of {pagination.total}{" "}
            items
          </span>
          <div className="flex gap-2">
            <button
              className="px-3 py-1 border border-gray-200 rounded hover:bg-white disabled:opacity-50"
              onClick={pagination.onPrev}
              disabled={pagination.currentPage <= 1}
            >
              Prev
            </button>
            <button
              className="px-3 py-1 border border-gray-200 rounded hover:bg-white disabled:opacity-50"
              onClick={pagination.onNext}
              disabled={pagination.currentPage >= pagination.totalPages}
            >
              Next
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default DataTable;
