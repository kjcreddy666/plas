import axios from 'axios';
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

//const API_URL = 'http://localhost:8080/emi';
const API_URL = '/api/emi';

export const previewEmi = async (data) =>
  handleRequest(
    () => axios.post(`${API_URL}/preview`, data, getAuthHeaders()),
    'EMI preview calculated successfully.'
  );

export const generateRepaymentSchedule = async (loanId, annualRate, page = 0, size = 12) =>
  handleRequest(
    () =>
      axios.post(
        `${API_URL}/repayments/${loanId}?annualRate=${annualRate}&page=${page}&size=${size}`,
        {},
        getAuthHeaders()
      ),
    'Repayment schedule generated successfully.'
  );
