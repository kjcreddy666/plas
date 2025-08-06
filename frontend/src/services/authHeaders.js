export const getAuthHeaders = () => ({
    headers: {
      Authorization: `Bearer ${localStorage.getItem('userToken')}`,
    },
  });
  