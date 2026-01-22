import React, { useEffect, useState } from "react";
import {
  getStudentDashboard,
  downloadClearancePdf,
} from "../services/dashboardService";

const StudentDashboard = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    getStudentDashboard()
      .then((res) => setData(res.data))
      .catch((e) => console.error(e));
  }, []);

  const handleDownload = (id) => {
    downloadClearancePdf(id)
      .then((res) => {
        const url = window.URL.createObjectURL(
          new Blob([res.data], { type: "application/pdf" }),
        );
        const link = document.createElement("a");
        link.href = url;
        link.setAttribute("download", `clearance-${id}.pdf`);
        document.body.appendChild(link);
        link.click();
        link.remove();
      })
      .catch((err) => console.error(err));
  };

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-2">Student Dashboard</h1>
      <div className="text-sm text-gray-600 mb-4">
        {data?.organizationalUnitName}
      </div>

      {data ? (
        <div>
          <div className="flex gap-4 mb-6">
            <div className="bg-white rounded shadow p-4 w-48">
              <div className="text-sm text-gray-500">Clearance Requested</div>
              <div className="text-2xl font-bold">
                {data.clearanceRequested}
              </div>
            </div>
            <div className="bg-white rounded shadow p-4 w-48">
              <div className="text-sm text-gray-500">Lost Cards</div>
              <div className="text-2xl font-bold">{data.lostCards}</div>
            </div>
          </div>

          <div className="bg-white rounded shadow p-4">
            <h2 className="font-semibold mb-3">Clearances</h2>
            <table className="w-full text-left">
              <thead>
                <tr className="text-sm text-gray-500">
                  <th>ID</th>
                  <th>Status</th>
                  <th>Requested</th>
                  <th>Completed</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {data.clearances.map((c) => (
                  <tr key={c.id} className="border-t">
                    <td className="py-2">{c.id}</td>
                    <td className="py-2">{c.status}</td>
                    <td className="py-2">
                      {c.requestDate
                        ? new Date(c.requestDate).toLocaleString()
                        : "-"}
                    </td>
                    <td className="py-2">
                      {c.completionDate
                        ? new Date(c.completionDate).toLocaleString()
                        : "-"}
                    </td>
                    <td className="py-2">
                      <button
                        onClick={() => handleDownload(c.id)}
                        className="px-3 py-1 bg-blue-600 text-white rounded"
                      >
                        Download PDF
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      ) : (
        <div>Loading...</div>
      )}
    </div>
  );
};

export default StudentDashboard;
