// composables/useAuth.js
import { ref } from 'vue';
import { login, register } from '@/services/authService';

export function useAuth() {
  const loading = ref(false);
  const error = ref(null);
  const user = ref(null); // optional: use Pinia/Vuex if needed

  const loginUser = async (credentials) => {
    loading.value = true;
    error.value = null;
  
    try {
      const data = await login(credentials);
      user.value = data;
      console.log(data)
  
      localStorage.setItem('success', data.isSuccess);
      localStorage.setItem('message', data.message);
      localStorage.setItem('token', data.token);
      localStorage.setItem('id', data.id);
      localStorage.setItem('role', data.role);
  
      return data;
    } catch (err) {
      error.value = err.response?.data?.message || 'Login failed';
      throw err;
    } finally {
      loading.value = false;
    }
  };
  

  const registerUser = async (details) => {
    loading.value = true;
    error.value = null;

    try {
      const data = await register(details);
      user.value = data.user;
      return data;
    } catch (err) {
      error.value = err.response?.data?.message || 'Registration failed';
      throw err;
    } finally {
      loading.value = false;
    }
  };

  return {
    loading,
    error,
    user,
    loginUser,
    registerUser,
  };
}
