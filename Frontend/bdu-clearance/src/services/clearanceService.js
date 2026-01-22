import api from "./api";

const BASE_URL = "/clearances";

const createClearance = async (data) => {
  await api.post(BASE_URL, data);
};

const getAllClearances = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

const getClearanceByStudentId = async (studentId) => {
  const response = await api.get(`${BASE_URL}/student/${studentId}`);
  return response.data;
};

const updateClearance = async (id, data) => {
  await api.put(`${BASE_URL}/${id}`, data);
};

const deleteClearance = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};

const downloadClearancePdf = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}/pdf`, {
    responseType: "blob", // Important for handling binary data
  });
  return response.data;
};

const clearanceService = {
  createClearance,
  getAllClearances,
  getClearanceByStudentId,
  updateClearance,
  deleteClearance,
  downloadClearancePdf,
};

export default clearanceService;
