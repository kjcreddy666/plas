export const handleRequest = async (requestFn, successMessage) => {
  try {
    const response = await requestFn();

    // Handle 204 No Content
    if (response.status === 204) {
      return {
        isSuccess: true,
        message: 'No content available.',
        data: null,
      };
    }

    return {
      isSuccess: true,
      message: successMessage,
      data: response.data,
    };
  } catch (error) {
    console.error(error);
    return {
      isSuccess: false,
      message: error.response?.data?.message || 'Something went wrong.',
      data: null,
    };
  }
};