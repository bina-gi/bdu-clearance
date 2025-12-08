import { useState } from "react";
import { Search, Filter } from "lucide-react";
import ClearanceTable from "../../ui/ClearanceTable";
import PageHeader from "../../ui/components/PageHeader";

export default function Staff() {
  const [students] = useState([
    {
      id: "ST-001",
      firstName: "Sarah",
      middleName: "Jane",
      lastName: "Connor",
      department: "Computer Science",
      year: "3rd Year",
      semester: "1st Sem",
      sectors: [
        "Library",
        "Cafe",
        "Dormitory",
        "Registrar",
        "faculty",
        "Advisor",
        "Store",
      ],
      status: "Approved",
      accadamicYear: "2023-2024",
    },
    {
      id: "ST-002",
      firstName: "James",
      middleName: "T.",
      lastName: "Kirk",
      department: "Astrophysics",
      year: "4th Year",
      sectors: ["Gym", "Cafe"],
      status: "Pendding",
    },
    {
      id: "ST-003",
      firstName: "Ellen",
      middleName: "L.",
      lastName: "Ripley",
      department: "Engineering",
      year: "2nd Year",
      sectors: ["Library", "Lab", "Dormitory", "Gym"],
      status: "Denied",
    },
    {
      id: "ST-004",
      firstName: "Marty",
      middleName: "Seamus",
      lastName: "McFly",
      department: "History",
      year: "1st Year",
      sectors: ["Cafe"],
      status: "Approved",
    },
  ]);

  return (
    <div className="min-h-screen bg-gray-50 p-4 lg:p-8 font-sans text-slate-800">
      <div className="max-w-6xl  mx-auto mb-6 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4">
        <PageHeader
          title="Student Directory"
          description="Manage student access and sectors."
        />

        <div className="flex gap-2 max-w-full sm:w-auto">
          <div className="relative grow sm:grow-0">
            <Search
              className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400"
              size={16}
            />
            <input
              type="text"
              placeholder="Search students..."
              className="pl-9 pr-4 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-500 max-w-full"
            />
          </div>
          <button className="p-2 border border-gray-200 rounded-lg hover:bg-gray-100 text-gray-600">
            <Filter size={18} />
          </button>
        </div>
      </div>

      <ClearanceTable students={students} />
    </div>
  );
}
