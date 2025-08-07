// composables/useLoan.js
import { ref } from 'vue';
import { useApi } from './useApi';
import {
  applyForLoan,
  getApplicationsByUser,
  getApplicationById
} from '@/services/loanService';

export const useLoan = () => {
  const loans = ref([]);
  const loan = ref(null);
  const { loading, error, execute } = useApi();

  const apply = async (formData) => {
    return await execute(() => applyForLoan(formData));
  };

  const fetchLoansByUser = async (userId) => {
    const response = await execute(() => getApplicationsByUser(userId));
    if (response.isSuccess) loans.value = response.data;
  };

  const fetchLoanById = async (loanId) => {
    const response = await execute(() => getApplicationById(loanId));
    if (response.isSuccess) loan.value = response.data;
  };

  return {
    loans,
    loan,
    error,
    loading,
    apply,
    fetchLoansByUser,
    fetchLoanById
  };
};
