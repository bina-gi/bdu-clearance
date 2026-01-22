import api from "./api";

export const getStaffDashboard = () => api.get("/dashboard/staff");
export const getStudentDashboard = () => api.get("/dashboard/student");

export const downloadClearancePdf = (id) =>
  api.get(`/clearance/${id}/pdf`, { responseType: "blob" });

export default { getStaffDashboard, getStudentDashboard, downloadClearancePdf };
