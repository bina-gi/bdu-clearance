import React, { useEffect, useState } from "react";
import { getStaffDashboard } from "../services/dashboardService";

const Card = ({ title, value }) => (
  <div className="bg-white rounded shadow p-4 w-48">
    <div className="text-sm text-gray-500">{title}</div>
    <div className="text-2xl font-bold">{value}</div>
  </div>
);

const StaffDashboard = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    getStaffDashboard()
      .then((res) => setData(res.data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-2">Staff Dashboard</h1>
      <div className="text-sm text-gray-600 mb-4">
        {data?.organizationalUnitName}
      </div>

      {data ? (
        <div className="flex gap-4 flex-wrap">
          <Card title="Total Clearances" value={data.totalClearances} />
          <Card title="Approved" value={data.approved} />
          <Card title="Pending" value={data.pending} />
          <Card title="Rejected" value={data.rejected} />

          <Card title="Users" value={data.usersCount} />
          <Card title="Org Units" value={data.organizationalUnitsCount} />
          <Card title="Unit Types" value={data.organizationalUnitTypesCount} />
          <Card title="Roles" value={data.rolesCount} />
        </div>
      ) : (
        <div>Loading...</div>
      )}
    </div>
  );
};

export default StaffDashboard;
