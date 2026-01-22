import api from "./api";

const BASE_URL = "/public/lost-card-reports";

/**
 * Create a new lost card report
 * @param {Object} data - { cardType: string, proofDocUrl: string, studentId: number }
 */
const createLostCardReport = async (data) => {
  await api.post(`${BASE_URL}/create`, data);
};

/**
 * Get all lost card reports (admin)
 */
const getAllLostCardReports = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

/**
 * Get lost card report by ID
 * @param {number} id
 */
const getLostCardReportById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);
  return response.data;
};

/**
 * Get lost card reports for a specific student
 * @param {number} studentId
 */
const getLostCardReportsByStudentId = async (studentId) => {
  const response = await api.get(`${BASE_URL}/student/${studentId}`);
  return response.data;
};

/**
 * Update a lost card report
 * @param {number} id
 * @param {Object} data
 */
const updateLostCardReport = async (id, data) => {
  await api.put(`${BASE_URL}/${id}`, data);
};

/**
 * Delete a lost card report
 * @param {number} id
 */
const deleteLostCardReport = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};

/**
 * Process a lost card report (approve/reject)
 * @param {number} id - Report ID
 * @param {string} status - APPROVED or REJECTED
 * The backend will use the authenticated user as the processor.
 */
const processLostCardReport = async (id, status) => {
  await api.patch(`${BASE_URL}/${id}/process`, null, {
    params: { status },
  });
};

/**
 * Upload a proof image file
 * @param {File} file - The image file to upload
 * @returns {Promise<string>} - The URL of the uploaded file
 */
const uploadProofImage = async (file) => {
  const formData = new FormData();
  formData.append("file", file);

  const response = await api.post("/public/upload", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data.url;
};

const lostCardReportService = {
  createLostCardReport,
  getAllLostCardReports,
  getLostCardReportById,
  getLostCardReportsByStudentId,
  updateLostCardReport,
  deleteLostCardReport,
  processLostCardReport,
  uploadProofImage,
};

export default lostCardReportService;
