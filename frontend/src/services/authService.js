import axios from 'axios';
import { handleRequest } from './requestHandler';

const API_URL = 'http://localhost:8080/api/auth';

export const register = async (data) =>
  handleRequest(
    () => axios.post(`${API_URL}/register`, data),
    'Registration successful.'
  );

export const login = async (data) =>
  handleRequest(
    () => axios.post(`${API_URL}/login`, data),
    'Login successful.'
  );