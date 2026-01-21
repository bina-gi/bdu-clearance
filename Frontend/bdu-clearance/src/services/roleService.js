import api from "./api";

const BASE_URL = "/public/roles";

/**
 * @typedef {Object} RoleRequest
 * @property {string} roleName - The role name (e.g., 'ADMIN', 'STUDENT', 'STAFF')
 */

/**
 * @typedef {Object} RoleResponse
 * @property {number} id - The role ID
 * @property {string} roleName - The role name
 */

/**
 * Get all roles
 * @returns {Promise<RoleResponse[]>}
 */
const getAllRoles = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

/**
 * Get a role by ID
 * @param {number} id
 * @returns {Promise<RoleResponse>}
 */
const getRoleById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);
  return response.data;
};

/**
 * Update a role
 * @param {number} id
 * @param {RoleRequest} roleData
 * @returns {Promise<void>}
 */
const updateRole = async (id, roleData) => {
  await api.put(`${BASE_URL}/${id}`, roleData);
};

/**
 * Delete a role
 * @param {number} id
 * @returns {Promise<void>}
 */
const deleteRole = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};

const roleService = {
  getAllRoles,
  getRoleById,
  updateRole,
  deleteRole,
};

export default roleService;
