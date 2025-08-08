import axios from 'axios';
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

//const BASE_URL = 'http://localhost:8080/users';
const API_URL = '/api/users';

export const createUser = async (userData) =>
  handleRequest(
    () => axios.post(API_URL, userData, getAuthHeaders()),
    'User created successfully.'
  );

export const getAllUsers = async () =>
  handleRequest(
    () => axios.get(API_URL, getAuthHeaders()),
    'All users fetched successfully.'
  );

export const getUserById = async (id) => {
  const result = await handleRequest(
    () => axios.get(`${API_URL}/${id}`, getAuthHeaders()),
    'User fetched by ID.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const getUserByEmail = async (email) => {
  const result = await handleRequest(
    () => axios.get(`${API_URL}/email/${email}`, getAuthHeaders()),
    'User fetched by email.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const getUserByMobile = async (mobile) => {
  const result = await handleRequest(
    () => axios.get(`${API_URL}/mobile/${mobile}`, getAuthHeaders()),
    'User fetched by mobile.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const updateUser = async (id, updatedData) => {
  const result = await handleRequest(
    () => axios.put(`${API_URL}/${id}`, updatedData, getAuthHeaders()),
    'User updated successfully.'
  );
  if (result.isSuccess) localStorage.setItem('user', JSON.stringify(result.data));
  return result;
};

export const updateUserProfile = async (id, profileData) => {
  try {
    // Get the current user data from localStorage or fetch it
    let currentUser = JSON.parse(localStorage.getItem('user') || '{}');
    
    // If we don't have current user data, fetch it first
    if (!currentUser.id) {
      console.log('No current user data found, fetching user first...');
      const userResult = await getUserById(id);
      if (!userResult.isSuccess) {
        return {
          isSuccess: false,
          error: 'Failed to fetch current user data',
          message: 'Could not retrieve current user information'
        };
      }
      currentUser = userResult.data;
    }

    // Create the complete user object with updated fields
    // Backend expects a full user object, handles null/empty fields internally
    const fullUserData = {
      id: currentUser.id,
      name: currentUser.name, // Keep existing name
      email: profileData.email?.trim() || currentUser.email,
      mobile: profileData.mobile?.trim() || currentUser.mobile,
      address: profileData.address?.trim() || currentUser.address,
      password: profileData.password?.trim() || currentUser.password,
      role: currentUser.role // Keep existing role
    };

    console.log('Full user data being sent to backend:', fullUserData);
    console.log('User ID:', id);

    // Use the existing updateUser method which sends PUT request
    const result = await updateUser(id, fullUserData);
    
    console.log('Update result from backend:', result);
    
    // If update failed, make sure we preserve the error message
    if (!result.isSuccess && result.originalError?.message) {
      result.message = result.originalError.message;
    }
    
    return result;
  } catch (error) {
    console.error('Error in updateUserProfile:', error);
    return {
      isSuccess: false,
      error: 'Failed to update user profile',
      message: error.response?.data?.message || error.message || 'Update failed'
    };
  }
};

export const deleteUser = async (id) => {
  const result = await handleRequest(
    () => axios.delete(`${API_URL}/${id}`, getAuthHeaders()),
    'User deleted successfully.'
  );
  if (result.isSuccess) localStorage.removeItem('user');
  return result;
};
