import { register, login } from '@/services/authService.js';
import { useUser } from '@/composables/useUser.js';

export const useAuth = () => {
  const { fetchUserById } = useUser();

  const registerUser = async (formData) => await register(formData);

  const loginUser = async (formData) => {
    const result = await login(formData);

    if (result.isSuccess && result.data.token) {
      const { id, role, email, token } = result.data;
      localStorage.setItem('userId', id);
      localStorage.setItem('userRole', role);
      localStorage.setItem('userEmail', email);
      localStorage.setItem('userToken', token);

      try {
        const user = (await fetchUserById(id)).data;
        localStorage.setItem('userName', user.name);
        localStorage.setItem('userEmail', user.email);
        localStorage.setItem('userMobile', user.mobile);
        localStorage.setItem('userAddress', user.address);
      } catch (error) {
        console.error('Failed to fetch user details:', error);
      }
    }

    return result;
  };

  return {
    register: registerUser,
    login: loginUser
  };
};
