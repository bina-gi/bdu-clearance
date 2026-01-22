import api from "./api";

const BASE_URL = "/public/organizational-units";

/**
 * @typedef {Object} OrganizationalUnitRequest
 * @property {string} organizationId - The organization ID (e.g. 'DEP-CS')
 * @property {string} organizationName - The organization name
 * @property {number} organizationalUnitTypeId - The type ID
 * @property {string} [parentOrganizationId] - The parent organization ID (optional)
 */

/**
 * @typedef {Object} OrganizationalUnitResponse
 * @property {number} id
 * @property {string} organizationId
 * @property {string} organizationName
 * @property {number} organizationalUnitTypeId
 * @property {string} organizationalUnitTypeName
 */

/**
 * Create a new organizational unit
 * @param {OrganizationalUnitRequest} data
 * @returns {Promise<void>}
 */
const createOrganizationalUnit = async (data) => {
  await api.post(BASE_URL, data);
};

/**
 * Get all organizational units
 * @returns {Promise<OrganizationalUnitResponse[]>}
 */
const getAllOrganizationalUnits = async () => {
  const response = await api.get(BASE_URL);
  return response.data;
};

/**
 * Get an organizational unit by ID
 * @param {number} id
 * @returns {Promise<OrganizationalUnitResponse>}
 */
const getOrganizationalUnitById = async (id) => {
  const response = await api.get(`${BASE_URL}/${id}`);
  return response.data;
};

/**
 * Update an organizational unit
 * @param {number} id
 * @param {OrganizationalUnitRequest} data
 * @returns {Promise<void>}
 */
const updateOrganizationalUnit = async (id, data) => {
  await api.put(`${BASE_URL}/${id}`, data);
};

/**
 * Delete an organizational unit
 * @param {number} id
 * @returns {Promise<void>}
 */
const deleteOrganizationalUnit = async (id) => {
  await api.delete(`${BASE_URL}/${id}`);
};

const organizationalUnitService = {
  createOrganizationalUnit,
  getAllOrganizationalUnits,
  getOrganizationalUnitById,
  updateOrganizationalUnit,
  deleteOrganizationalUnit,
};

export default organizationalUnitService;
