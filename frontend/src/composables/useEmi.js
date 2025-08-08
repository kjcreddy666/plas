import { ref } from 'vue';
import { useApi } from './useApi';
import {
  previewEmi,
  generateRepaymentSchedule
} from '@/services/emiService';

export const useEmi = () => {
  const emiData = ref(null);
  const schedule = ref([]);
  const totalItems = ref(0);
  const currentPage = ref(0);
  const pageSize = ref(12);

  const { loading, error, execute } = useApi();

  const calculatePreview = async (formData) => {
    const response = await execute(() => previewEmi(formData));
    if (response.isSuccess) emiData.value = response.data;
  };

  const fetchRepaymentSchedule = async (loanId, annualRate, page = 0, size = 12) => {
    const response = await execute(() =>
      generateRepaymentSchedule(loanId, annualRate, page, size)
    );
    if (response.isSuccess) {
      schedule.value = response.data.content;
      totalItems.value = response.data.totalElements;
      currentPage.value = response.data.page;
      pageSize.value = response.data.size;
    }
  };

  return {
    emiData,
    schedule,
    totalItems,
    currentPage,
    pageSize,
    error,
    loading,
    calculatePreview,
    fetchRepaymentSchedule
  };
};
