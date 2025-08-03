<template>
  <div class="auth-form">
    <h2 class="auth-heading">Register Page</h2>
    <form @submit.prevent="handleRegister">
      <div class="auth-form-group">
        <label class="auth-label">
          Full Name <span class="auth-required">*</span>
        </label>
        <input
          class="auth-input"
          type="text"
          placeholder="Enter Full Name"
          v-model="details.name"
          required
        />
      </div>

      <div class="auth-form-group">
        <label class="auth-label">
          Email <span class="auth-required">*</span>
        </label>
        <input
          class="auth-input"
          type="email"
          placeholder="Enter Email"
          v-model="details.email"
          required
        />
      </div>

      <div class="auth-form-group">
        <label class="auth-label">
          Mobile <span class="auth-required">*</span>
        </label>
        <input
          class="auth-input"
          type="tel"
          placeholder="Enter Mobile"
          v-model="details.mobile"
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
          v-model="details.password"
          required
        />
      </div>

      <div class="auth-form-group">
        <label class="auth-label">
          Address <span class="auth-required">*</span>
        </label>
        <textarea
          class="auth-textarea"
          placeholder="Enter Address"
          v-model="details.address"
          required
        ></textarea>
      </div>

      <div class="auth-actions">
        <a class="auth-link" href="/auth?tab=login">Login</a>
        <button class="auth-button" type="submit" :disabled="loading">
          {{ loading ? 'Registering...' : 'Register' }}
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
const { registerUser, loading, error } = useAuth();

const details = reactive({
  nmae: '',
  email: '',
  mobile: '',
  password: '',
  address: '',
});

const handleRegister = async () => {
  try {
    await registerUser(details);
    router.push('/auth?tab=login'); // 🔥 redirect to login after success
  } catch (err) {
    // Error is already handled in composable
  }
};
</script>


<style src="@/assets/auth.css"></style>
