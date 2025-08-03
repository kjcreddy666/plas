<template>
  <div class="auth-form">
    <h2 class="auth-heading">Login Page</h2>
    <form @submit.prevent="handleLogin">
      <div class="auth-form-group">
        <label class="auth-label">
          Email or Mobile <span class="auth-required">*</span>
        </label>
        <input
          class="auth-input"
          type="text"
          placeholder="Enter Email or Mobile"
          v-model="credentials.email"
          required
        />
      </div>

      <div class="auth-form-group">
        <label class="auth-label">
          Password <span class="auth-required">*</span>
        </label>
        <input
          class="auth-input"
          type="password"
          placeholder="Enter Password"
          v-model="credentials.password"
          required
        />
      </div>

      <div class="auth-actions">
        <a class="auth-link" href="/auth?tab=register">Register</a>
        <button class="auth-button" type="submit" :disabled="loading">
          {{ loading ? 'Logging in...' : 'Login' }}
        </button>
      </div>

      <p v-if="error" style="color: red; margin-top: 1rem;">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { useAuth } from '@/composables/useAuth';

const router = useRouter();
const { loginUser, loading, error } = useAuth();

const credentials = reactive({
  email: '',
  password: '',
});

const handleLogin = async () => {
  try {
    await loginUser(credentials);
    router.push('/user'); // 🔥 redirect after success
  } catch (err) {
    // error handled in composable
  }
};
</script>


<style src="@/assets/auth.css"></style>
