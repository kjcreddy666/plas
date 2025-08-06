import { ref } from 'vue';
import { useApi } from './useApi';
import {
  createTicket,
  getTicketsByUser,
  getTicketById
} from '@/services/ticketService';

export const useTicket = () => {
  const tickets = ref([]);
  const ticket = ref(null);
  const { loading, error, execute } = useApi();

  const create = async (formData) => {
    return await execute(() => createTicket(formData));
  };

  const fetchTicketsByUser = async (userId) => {
    const response = await execute(() => getTicketsByUser(userId));
    if (response.isSuccess) tickets.value = response.data;
  };

  const fetchTicketById = async (ticketId) => {
    const response = await execute(() => getTicketById(ticketId));
    if (response.isSuccess) ticket.value = response.data;
  };

  return {
    tickets,
    ticket,
    error,
    loading,
    create,
    fetchTicketsByUser,
    fetchTicketById
  };
};
