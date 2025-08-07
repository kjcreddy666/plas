import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

export const useAuthGuard = () => {
  const router = useRouter();

  const userId = ref(localStorage.getItem('userId'));
  const token = ref(localStorage.getItem('userToken'));
  const role = ref(localStorage.getItem('userRole'));

  onMounted(() => {
    if (!token.value || !userId.value || !role.value) {
      router.push('/auth');
    }
  });

  return { userId, token, role };
};