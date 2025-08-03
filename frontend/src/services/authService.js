import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // Replace with your backend URL

export const login = async (payload) => {
  const response = await axios.post(`${API_URL}/auth/login`, payload);
  return response.data;
};

export const register = async (payload) => {
  const response = await axios.post(`${API_URL}/auth/register`, payload);
  return response.data;
};
