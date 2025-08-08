import { ref } from 'vue';
import { useApi } from './useApi';
import {
  getAllLoanApplications,
  getFilteredLoanApplications,
  updateLoanStatus,
  getAllTickets,
  updateTicketStatus,
  getAllUsers
} from '@/services/adminService';

export const useAdmin = () => {
  const loans = ref([]);
  const tickets = ref([]);
  const users = ref([]);
  const { loading, error, execute } = useApi();

  const fetchAllLoans = async () => {
    const response = await execute(() => getAllLoanApplications());
    if (response.isSuccess) loans.value = response.data;
  };

  const fetchFilteredLoans = async (statuses) => {
    const response = await execute(() => getFilteredLoanApplications(statuses));
    if (response.isSuccess) loans.value = response.data;
  };

  const changeLoanStatus = async (loanId, status, remarks) => {
    return await execute(() =>
      updateLoanStatus(loanId, { status, remarks })
    );
  };

  const fetchTickets = async () => {
    console.log('🎫 useAdmin: Fetching tickets from API...');
    const response = await execute(() => getAllTickets());
    console.log('🎫 useAdmin: API response:', response);
    if (response.isSuccess) {
      tickets.value = response.data;
      console.log('🎫 useAdmin: Tickets stored:', tickets.value);
    } else {
      console.log('🎫 useAdmin: Failed to fetch tickets:', response.error);
    }
  };

  const resolveTicket = async (ticketId, responseMessage) => {
    return await execute(() =>
      updateTicketStatus(ticketId, { response: responseMessage })
    );
  };

  const fetchUsers = async (page = 0, size = 10) => {
    const response = await execute(() => getAllUsers(page, size));
    if (response.isSuccess) users.value = response.data.content;
  };

  return {
    loans,
    tickets,
    users,
    loading,
    error,
    fetchAllLoans,
    fetchFilteredLoans,
    changeLoanStatus,
    fetchTickets,
    resolveTicket,
    fetchUsers
  };
};
