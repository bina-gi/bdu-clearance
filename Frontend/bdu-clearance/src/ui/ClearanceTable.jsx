import ClearanceRow from "./components/ClearanceRow";

export default function ClearanceTable({ students }) {
  const prevHandler = () => {};
  const nextHandler = () => {};

  return (
    <div className="max-w-6xl  mx-auto bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden">
      <div className="overflow-x-auto">
        <table className="max-w-full min-w-[660px] text-left border-collapse">
          <thead>
            <tr className="bg-gray-50 border-b border-gray-200 text-xs uppercase tracking-wider text-gray-500 font-semibold">
              <th className="p-1 lg:p-2 xl:p-4 w-24">DATE</th>
              <th className="p-1 lg:p-2 xl:p-4">YEAR/SEME</th>
              <th className="p-1 lg:p-2 xl:p-4 xl:min-w-40">REASON</th>
              <th className="p-1 lg:p-2 xl:p-4">ACCESS SECTORS</th>
              <th className="p-1 lg:p-2 xl:p-4">STATUS</th>
              <th className="p-1 lg:p-2 xl:p-4 text-right">ACTIONS</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {students.map((student) => (
              <ClearanceRow key={student.id} student={student} />
            ))}
          </tbody>
        </table>
      </div>

      <div className="p-4 border-t border-gray-200 bg-gray-50 flex justify-between items-center text-xs text-gray-500">
        <span>Showing 4 of 128 students</span>
        <div className="flex gap-2">
          <button
            className="px-3 py-1 border border-gray-200 rounded hover:bg-white disabled:opacity-50"
            onClick={prevHandler}
          >
            Prev
          </button>
          <button
            className="px-3 py-1 border border-gray-200 rounded hover:bg-white"
            onClick={nextHandler}
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}
