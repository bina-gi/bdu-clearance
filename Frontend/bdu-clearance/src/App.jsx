import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Toaster } from "react-hot-toast";
import "./App.css";

import AppLayout from "./ui/AppLayout";
import Home from "./pages/Home";
import RequestClearance from "./features/Student/RequestClearance";
import Search from "./features/Student/Search";
import Role from "./features/Role/Role";
import OrganizationalUnitType from "./features/OrganizationalUnitType/OrganizationalUnitType";

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
            <Route path="roles" element={<Role />} />
            <Route
              path="organization-types"
              element={<OrganizationalUnitType />}
            />
          </Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
