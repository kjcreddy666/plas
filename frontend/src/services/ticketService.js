import axios from 'axios';
import { handleRequest } from './requestHandler';
import { getAuthHeaders } from './authHeaders';

const API_URL = 'http://localhost:8080/support';

export const createTicket = async (data) =>
  handleRequest(
    () => axios.post(`${API_URL}/create`, data, getAuthHeaders()),
    'Support ticket created successfully.'
  );

export const getTicketsByUser = async (userId) =>
  handleRequest(
    () => axios.get(`${API_URL}/user/${userId}`, getAuthHeaders()),
    'Support tickets fetched successfully.'
  );

export const getTicketById = async (ticketId) =>
  handleRequest(
    () => axios.get(`${API_URL}/ticket/${ticketId}`, getAuthHeaders()),
    'Support ticket fetched successfully.'
  );