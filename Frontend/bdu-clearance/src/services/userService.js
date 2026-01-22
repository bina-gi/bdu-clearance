import api from "./api";

const BASE_URL = "/public/users";

/**
 * @typedef {Object} UserRequest
 * @property {string} userId
 * @property {string} firstName
 * @property {string} middleName
 * @property {string} lastName
 * @property {number} roleId
 * @property {number} organizationalUnitId
 * @property {boolean} [isActive]
 */

/**
 * @typedef {Object} UserResponse
 * @property {number} id
 * @property {string} userId
 * @property {string} firstName
 * @property {string} middleName
 * @property {string} lastName
 * @property {boolean} isActive
 * @property {number} roleId
 * @property {string} roleName
 * @property {number} organizationalUnitId
 * @property {string} organizationalUnitName
 * @property {boolean} isStudent
 */

/**
 * Create a new user
 * @param {UserRequest} data
 * @returns {Promise<void>}
 */
const createUser = async (data) => {
  await api.post(BASE_URL, data);
};

/**
 * Get all users
 * @returns {Promise<UserResponse[]>}
 */
const getAllUsers = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

/**
 * Get user by User ID (String)
 * @param {string} userId
 * @returns {Promise<UserResponse>}
 */
const getUserById = async (userId) => {
  const response = await api.get(`${BASE_URL}/${userId}`);
  return response.data;
};

/**
 * Update user
 * @param {number} id
 * @param {UserRequest} data
 * @returns {Promise<void>}
 */
const updateUser = async (id, data) => {
  await api.put(`${BASE_URL}/${id}`, data);
};

/**
 * Delete user
 * @param {string} userId
 * @returns {Promise<void>}
 */
const deleteUser = async (userId) => {
  await api.delete(`${BASE_URL}/${userId}`);
};

/**
 * Reset password
 * @param {string} userId
 * @returns {Promise<void>}
 */
const resetPassword = async (userId) => {
  await api.put(`${BASE_URL}/reset-password/${userId}`);
};

const userService = {
  createUser,
  getAllUsers,
  getUserById,
  updateUser,
  deleteUser,
  resetPassword,
};

export default userService;
