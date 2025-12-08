import { Download, File } from "lucide-react";
import TableButton from "./TableButton";
import TableCell from "./TableCell";

export default function ClearanceRow({ student }) {
  return (
    <tr className="hover:bg-gray-50/50 transition-colors group">
      <TableCell>
        <span className="font-mono text-xs font-medium text-gray-500 bg-gray-100 px-2 py-1 rounded">
          {student.id}
        </span>
      </TableCell>

      <TableCell className="flex items-center gap-3">
        <div>
          <div className="font-semibold text-gray-900">
            {student.firstName} {student.middleName} {student.lastName}
          </div>
          <div className="text-xs text-gray-500 mt-0.5">{student.status}</div>
        </div>
      </TableCell>

      <TableCell className="flex flex-col">
        <span className="text-sm font-medium text-gray-900">
          {student.department}
        </span>
        <span className="text-xs text-blue-600 bg-blue-50 px-2 py-0.5 rounded-full w-fit mt-1 border border-blue-100">
          {student.year}
        </span>
      </TableCell>

      <TableCell className="flex flex-wrap gap-1 md:gap-2">
        {student.sectors.map((sector, idx) => (
          <span
            key={idx}
            className="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-md text-xs font-medium bg-slate-100 text-slate-600 border border-slate-200"
          >
            {sector}
          </span>
        ))}
      </TableCell>

      <TableCell className="flex items-center justify-start gap-2">
        <span
          className={`rounded-full font-semibold text-xs px-2  ${student.status == "Approved" ? "bg-green-300 text-green-700" : student.status == "Pendding" ? "bg-yellow-300 text-yellow-700" : "bg-red-300 text-red-500"}`}
        >
          {student.status}
        </span>
      </TableCell>
      <TableCell className="flex items-center justify-end gap-2">
        <TableButton title="download clearance" icon={<Download />} />
        <span className="text-red-800 bg-red-300 rounded p-2">
          <File />
          pdf
        </span>
      </TableCell>
    </tr>
  );
}
