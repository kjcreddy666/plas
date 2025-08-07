<template>
  <div>
    <form class="p-4 needs-validation" novalidate @submit.prevent="validateForm">
      <h5 class="mb-4"><i class="bi bi-box-arrow-in-right"></i> Sign In</h5>

      <!-- Error Message -->
      <div v-if="error" class="alert alert-danger">{{ error }}</div>

      <!-- Email Field -->
      <div class="mb-4">
        <label>Email Address <span class="text-danger">*</span></label>
        <input
          v-model="email"
          type="email"
          autocomplete="off"
          class="form-control"
          :class="{ 'is-invalid': submitted && !validEmail() }"
          placeholder="Enter your email address"
          required
        />
        <div class="invalid-feedback">Please enter a valid email address.</div>
      </div>

      <!-- Password Field -->
      <div class="mb-4">
        <label>Password <span class="text-danger">*</span></label>
        <div class="input-group">
          <input
            v-model="password"
            :type="showPassword ? 'text' : 'password'"
            autocomplete="off"
            class="form-control no-eye"
            :class="{ 'is-invalid': submitted && !password }"
            placeholder="Enter your password"
            required
          />
          <span class="input-group-text" style="cursor: pointer;" @click="togglePassword">
            <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
          </span>
          <div class="invalid-feedback">Password is required.</div>
        </div>
      </div>

      <div class="mb-4 text-end">
        <a href="#">Forgot your password?</a>
      </div>

      <button class="btn btn-primary w-100" type="submit">Sign In →</button>
    </form>

    <!-- Success Modal -->
    <div
      class="modal fade"
      id="successModal"
      tabindex="-1"
      aria-labelledby="successModalLabel"
      aria-hidden="true"
      ref="successModal"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content" style="background-color: #198754; color: white;">
          <div class="modal-header border-0">
            <h5 class="modal-title" id="successModalLabel">Login Successful</h5>
          </div>
          <div class="modal-body text-center">
            <p>Welcome back!</p>
            <p class="fw-bold">Redirecting to dashboard...</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuth } from '@/composables/useAuth.js';

export default {
  setup() {
    const email = ref('');
    const password = ref('');
    const submitted = ref(false);
    const showPassword = ref(false);
    const error = ref('');
    const successModal = ref(null);
    const router = useRouter();
    let modalInstance = null;

    const { login } = useAuth();

    const validEmail = () => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email.value);

    const togglePassword = () => {
      showPassword.value = !showPassword.value;
    };

    const validateForm = async () => {
      submitted.value = true;
      error.value = '';

      if (!validEmail() || !password.value) {
        error.value = 'Please fill all required fields correctly.';
        return;
      }

      try {
        const result = await login({ email: email.value, password: password.value });
        console.log(result)
        if (result.isSuccess && result.data.token) {
          showSuccessPopup();
        } else {
          error.value = result.message || 'Invalid email or password.';
        }
      } catch (err) {
        error.value = err.message || 'Something went wrong.';
      }
    };

    const showSuccessPopup = () => {
      if (!modalInstance) return;
      modalInstance.show();

      setTimeout(() => {
        modalInstance.hide();
        console.log('redirecting...')
        router.push('/dashboard');
      }, 1500);
    };

    onMounted(() => {
      if (successModal.value && window.bootstrap) {
        modalInstance = new window.bootstrap.Modal(successModal.value);
      }
    });

    return {
      email,
      password,
      submitted,
      showPassword,
      error,
      successModal,
      validEmail,
      togglePassword,
      validateForm,
    };
  },
};
</script>

<style scoped>
.no-eye {
  appearance: none;
}
</style>
