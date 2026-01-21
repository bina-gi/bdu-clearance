import { Pencil, Trash2 } from "lucide-react";

function DataListCard({
  title,
  icon: Icon,
  onEdit,
  onDelete,
  editLabel = "Edit",
  deleteLabel = "Delete",
}) {
  return (
    <div className="bg-white rounded-xl shadow-sm hover:shadow-md transition-all duration-200 p-5 border border-gray-100 flex items-center justify-between">
      <div className="flex items-center gap-5">
        <div className="p-3 bg-blue-50 text-blue-600 rounded-xl">
          <Icon size={24} />
        </div>
        <span className="text-lg font-semibold text-gray-800">{title}</span>
      </div>

      <div className="flex gap-3">
        <button
          onClick={onEdit}
          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
          title={editLabel}
          aria-label={editLabel}
        >
          <Pencil size={18} />
        </button>
        <button
          onClick={onDelete}
          className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition-colors"
          title={deleteLabel}
          aria-label={deleteLabel}
        >
          <Trash2 size={18} />
        </button>
      </div>
    </div>
  );
}

export default DataListCard;
