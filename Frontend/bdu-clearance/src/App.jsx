import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import "./App.css";

import AppLayout from "./ui/AppLayout";
import Home from "./pages/Home";
import RequestClearance from "./features/Student/RequestClearance";
import ReportLostCard from "./features/Student/ReportLostCard";
import Search from "./features/Student/Search";
import Role from "./features/Role/Role";
import OrganizationalUnitType from "./features/OrganizationalUnitType/OrganizationalUnitType";
import OrganizationalUnit from "./features/OrganizationalUnit/OrganizationalUnit";
import UserManagement from "./features/User/UserManagement";
import Account from "./pages/Account";
import StaffClearanceDashboard from "./features/Staff/StaffClearanceDashboard";
import LostCards from "./features/Staff/LostCards";
import StaffDashboard from "./pages/StaffDashboard";
import StudentDashboard from "./pages/StudentDashboard";
import AdminDashboard from "./pages/AdminDashboard";

import Login from "./pages/Login";
import ProtectedRoute from "./ui/ProtectedRoute";

function App() {
  return (
    <>
      <Toaster
        position="top-center"
        gutter={12}
        containerStyle={{ margin: "8px" }}
        toastOptions={{
          success: {
            duration: 3000,
          },
          error: {
            duration: 5000,
          },
          style: {
            fontSize: "16px",
            maxWidth: "500px",
            padding: "16px 24px",
            backgroundColor: "white",
            color: "var(--color-grey-700)",
          },
        }}
      />
      <BrowserRouter>
        <Routes>
          <Route path="login" element={<Login />} />
          <Route
            element={
              <ProtectedRoute>
                <AppLayout />
              </ProtectedRoute>
            }
          >
            <Route path="/" element={<Home />} />
            <Route path="request-clearance" element={<RequestClearance />} />
            <Route path="search" element={<Search />} />
            <Route path="user-management" element={<UserManagement />} />
            <Route path="account" element={<Account />} />

            {/* Clearance Routes */}
            <Route path="request-clearance" element={<RequestClearance />} />
            <Route path="report-lost-card" element={<ReportLostCard />} />
            <Route
              path="requested-clearances"
              element={<StaffClearanceDashboard />}
            />
            <Route path="staff-dashboard" element={<StaffDashboard />} />
            <Route path="student-dashboard" element={<StudentDashboard />} />
            <Route path="admin-dashboard" element={<AdminDashboard />} />
            <Route path="lost-card-reports" element={<LostCards />} />

            <Route path="roles" element={<Role />} />
            <Route
              path="organization-types"
              element={<OrganizationalUnitType />}
            />
            <Route
              path="organizational-unit"
              element={<OrganizationalUnit />}
            />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
