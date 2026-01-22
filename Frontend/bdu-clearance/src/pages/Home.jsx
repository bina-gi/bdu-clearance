import React from "react";
import useAuth from "../hooks/useAuth";
import StaffDashboard from "./StaffDashboard";
import StudentDashboard from "./StudentDashboard";
import AdminDashboard from "./AdminDashboard";

function Home() {
  const { role } = useAuth();

  if (!role) return null;

  // Treat ADVISOR as STAFF as requested
  if (role === "ADMIN") return <AdminDashboard />;
  if (role === "STAFF" || role === "ADVISOR") return <StaffDashboard />;
  if (role === "STUDENT") return <StudentDashboard />;

  return null;
}

export default Home;
