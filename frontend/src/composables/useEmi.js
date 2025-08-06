// composables/useEmi.js
import { ref } from 'vue';
import { useApi } from './useApi';
import {
  previewEmi,
  generateRepaymentSchedule
} from '@/services/emiService';

export const useEmi = () => {
  const emiData = ref(null);
  const schedule = ref([]);
  const { loading, error, execute } = useApi();

  const calculatePreview = async (formData) => {
    const response = await execute(() => previewEmi(formData));
    if (response.isSuccess) emiData.value = response.data;
  };

  const fetchRepaymentSchedule = async (loanId, annualRate) => {
    const response = await execute(() => generateRepaymentSchedule(loanId, annualRate));
    if (response.isSuccess) schedule.value = response.data;
  };

  return {
    emiData,
    schedule,
    error,
    loading,
    calculatePreview,
    fetchRepaymentSchedule
  };
};
