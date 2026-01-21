import api from "./api";

const BASE_URL = "/public/organizational-unit-types";

/**
 * @typedef {Object} OrganizationalUnitTypeRequest
 * @property {string} organizationType - The organization type name
 */

/**
 * @typedef {Object} OrganizationalUnitTypeResponse
 * @property {number} id - The ID
 * @property {string} organizationType - The organization type name
 */

/**
 * Create a new organizational unit type
 * @param {OrganizationalUnitTypeRequest} data
 * @returns {Promise<void>}
 */
const createOrganizationalUnitType = async (data) => {
  await api.post(`${BASE_URL}/create`, data);
};

/**
 * Get all organizational unit types
 * @returns {Promise<OrganizationalUnitTypeResponse[]>}
 */
const getAllOrganizationalUnitTypes = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

/**
 * Get an organizational unit type by ID
 * @param {number} id
 * @returns {Promise<OrganizationalUnitTypeResponse>}
 */
const getOrganizationalUnitTypeById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);
  return response.data;
};

/**
 * Update an organizational unit type
 * @param {number} id
 * @param {OrganizationalUnitTypeRequest} data
 * @returns {Promise<void>}
 */
const updateOrganizationalUnitType = async (id, data) => {
  await api.put(`${BASE_URL}/${id}`, data);
};

/**
 * Delete an organizational unit type
 * @param {number} id
 * @returns {Promise<void>}
 */
const deleteOrganizationalUnitType = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};

const organizationalUnitTypeService = {
  createOrganizationalUnitType,
  getAllOrganizationalUnitTypes,
  getOrganizationalUnitTypeById,
  updateOrganizationalUnitType,
  deleteOrganizationalUnitType,
};

export default organizationalUnitTypeService;
