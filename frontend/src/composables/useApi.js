import { ref } from 'vue';

export const useApi = () => {
  const loading = ref(false);
  const error = ref('');

  const execute = async (apiCall) => {
    loading.value = true;
    error.value = '';

    try {
      const response = await apiCall();

      if (response?.isSuccess === false) {
        error.value = response.message || 'Operation failed';
      }

      return response;
    } catch (err) {
      error.value = err.message || 'Unknown error';
      return { isSuccess: false, message: error.value };
    } finally {
      loading.value = false;
    }
  };

  return {
    loading,
    error,
    execute
  };
};
