import api from "./api";

const BASE_URL = "/public/students";

const createStudent = async (data) => {
  await api.post(`${BASE_URL}/create`, data);
};

const getAllStudents = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

const getStudent = async (studentId) => {
  const response = await api.get(`${BASE_URL}/${studentId}`);
  console.log("studentId", studentId);
  console.log(response.data);
  return response.data;
};

const updateStudent = async (id, data) => {
  await api.put(`${BASE_URL}/${id}`, data);
};

const deleteStudent = async (studentId) => {
  await api.delete(`${BASE_URL}/${studentId}`);
};

const studentService = {
  createStudent,
  getAllStudents,
  getStudent,
  updateStudent,
  deleteStudent,
};

export default studentService;
