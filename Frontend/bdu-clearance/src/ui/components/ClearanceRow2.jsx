import { Edit2, MoreHorizontal, Trash2 } from "lucide-react";

// New component for rendering a single table row
const ClearanceRow = ({ student }) => {
  return (
    <tr className="hover:bg-gray-50/50 transition-colors group">
      <td className="p-4 align-top">
        <span className="font-mono text-xs font-medium text-gray-500 bg-gray-100 px-2 py-1 rounded">
          {student.id}
        </span>
      </td>

      <td className="p-4 align-top">
        <div className="flex items-center gap-3">
          <div>
            <div className="font-semibold text-gray-900">
              {student.firstName} {student.middleName} {student.lastName}
            </div>
            <div className="text-xs text-gray-500 mt-0.5">{student.status}</div>
          </div>
        </div>
      </td>

      <td className="p-4 align-top">
        <div className="flex flex-col">
          <span className="text-sm font-medium text-gray-900">
            {student.department}
          </span>
          <span className="text-xs text-blue-600 bg-blue-50 px-2 py-0.5 rounded-full w-fit mt-1 border border-blue-100">
            {student.year}
          </span>
        </div>
      </td>

      {/* Column 4: List of Sectors */}
      <td className="p-4 align-top max-w-xs">
        {/* Using flex-wrap so tags don't stretch or overflow horizontally */}
        <div className="flex flex-wrap gap-2">
          {student.sectors.map((sector, idx) => (
            <span
              key={idx}
              className="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md text-xs font-medium bg-slate-100 text-slate-600 border border-slate-200"
            ></span>
          ))}
        </div>
      </td>

      {/* Column 5: Action Buttons ("Cat" Buttons) */}
      <td className="p-4 align-top text-right">
        <div className="flex items-center justify-end gap-2">
          <button
            className="p-2 text-gray-400 hover:text-blue-600 hover:bg-blue-50 rounded-lg transition-colors"
            title="Edit Student"
          >
            <Edit2 size={16} />
          </button>
        </div>
      </td>
    </tr>
  );
};
export default ClearanceRow;
