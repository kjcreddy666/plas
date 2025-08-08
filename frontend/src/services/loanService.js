import axios from 'axios';
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

//const API_URL = 'http://localhost:8080/loans';
const API_URL = '/api/loans';

export const applyForLoan = async (data) =>
  handleRequest(
    () => axios.post(`${API_URL}/apply`, data, getAuthHeaders()),
    'Loan application submitted successfully.'
  );

export const getApplicationsByUser = async (userId) =>
  handleRequest(
    () => axios.get(`${API_URL}/user/${userId}`, getAuthHeaders()),
    'Applications fetched successfully.'
  );

export const getApplicationById = async (loanId) =>
  handleRequest(
    () => axios.get(`${API_URL}/${loanId}`, getAuthHeaders()),
    'Application fetched successfully.'
  );
