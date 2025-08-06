import axios from 'axios';
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

const API_URL = 'http://localhost:8080/emi';

export const previewEmi = async (data) =>
  handleRequest(
    () => axios.post(`${API_URL}/preview`, data, getAuthHeaders()),
    'EMI preview calculated successfully.'
  );

export const generateRepaymentSchedule = async (loanId, annualRate) =>
  handleRequest(
    () => axios.post(`${API_URL}/repayments/${loanId}?annualRate=${annualRate}`, {}, getAuthHeaders()),
    'Repayment schedule generated successfully.'
  );
