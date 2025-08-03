<script setup>
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const user = ref({});

onMounted(() => {
  const isSuccess = localStorage.getItem('success');
  const token = localStorage.getItem('token');
  const id = localStorage.getItem('id');
  const role = localStorage.getItem('role');
  const message = localStorage.getItem('message');

  if (!isSuccess || !token || !id || !role) {
    console.log(isSuccess, token, id, role, message)
    router.push('/auth?tab=login');
  } else {
    user.value = {
      id,
      role,
      message,
      token,
    };
  }
});
</script>

<template>
    <div>
      <h1>User Dashboard</h1>
      <div class="user-details" v-if="user.id">
        <p><strong>User ID:</strong> {{ user.id }}</p>
        <p><strong>Role:</strong> {{ user.role }}</p>
        <p><strong>Message:</strong> {{ user.message }}</p>
        <p><strong>Token:</strong> {{ user.token }}</p>
      </div>
    </div>
</template>
  