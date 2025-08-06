import axios from 'axios';
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

const BASE_URL = 'http://localhost:8080/users';

export const createUser = async (userData) =>
  handleRequest(
    () => axios.post(BASE_URL, userData, getAuthHeaders()),
    'User created successfully.'
  );

export const getAllUsers = async () =>
  handleRequest(
    () => axios.get(BASE_URL, getAuthHeaders()),
    'All users fetched successfully.'
  );

export const getUserById = async (id) => {
  const result = await handleRequest(
    () => axios.get(`${BASE_URL}/${id}`, getAuthHeaders()),
    'User fetched by ID.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const getUserByEmail = async (email) => {
  const result = await handleRequest(
    () => axios.get(`${BASE_URL}/email/${email}`, getAuthHeaders()),
    'User fetched by email.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const getUserByMobile = async (mobile) => {
  const result = await handleRequest(
    () => axios.get(`${BASE_URL}/mobile/${mobile}`, getAuthHeaders()),
    'User fetched by mobile.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const updateUser = async (id, updatedData) => {
  const result = await handleRequest(
    () => axios.put(`${BASE_URL}/${id}`, updatedData, getAuthHeaders()),
    'User updated successfully.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const deleteUser = async (id) => {
  const result = await handleRequest(
    () => axios.delete(`${BASE_URL}/${id}`, getAuthHeaders()),
    'User deleted successfully.'
  );
  if (result.isSuccess) localStorage.removeItem('user');
  return result;
};
