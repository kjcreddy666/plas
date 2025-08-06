import axios from 'axios';
import qs from 'qs'; // make sure qs is installed: `npm install qs`
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

const API_URL = 'http://localhost:8080/admin';

export const getAllLoanApplications = async () =>
  handleRequest(
    () => axios.get(`${API_URL}/loans`, getAuthHeaders()),
    'All loan applications fetched successfully.'
  );

export const getFilteredLoanApplications = async (statuses) =>
  handleRequest(
    () =>
      axios.get(`${API_URL}/loans/filter`, {
        ...getAuthHeaders(),
        params: { statuses },
        paramsSerializer: (params) =>
          qs.stringify(params, { arrayFormat: 'repeat' }) // ensures ?statuses=A&statuses=B
      }),
    'Filtered loan applications fetched successfully.'
  );

export const updateLoanStatus = async (loanId, data) =>
  handleRequest(
    () => axios.put(`${API_URL}/loan/${loanId}/status`, data, getAuthHeaders()),
    'Loan status updated successfully.'
  );

export const getAllTickets = async () =>
  handleRequest(
    () => axios.get(`${API_URL}/tickets`, getAuthHeaders()),
    'Support tickets fetched successfully.'
  );

export const updateTicketStatus = async (ticketId, data) =>
  handleRequest(
    () => axios.put(`${API_URL}/ticket/${ticketId}`, data, getAuthHeaders()),
    'Ticket status updated successfully.'
  );

export const getAllUsers = async (page = 0, size = 10) =>
  handleRequest(
    () =>
      axios.get(`${API_URL}/users`, {
        ...getAuthHeaders(),
        params: { page, size }
      }),
    'Users fetched successfully.'
  );
