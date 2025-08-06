export const handleRequest = async (requestFn, successMessage) => {
    try {
      const response = await requestFn();
      return {
        isSuccess: true,
        message: successMessage,
        data: response.data,
      };
    } catch (error) {
      console.log(error)
      return {
        isSuccess: false,
        message: error.response?.data?.message || 'Something went wrong.',
      };
    }
};