<template>
  <div>
    <!-- Form Start -->
    <form class="p-4 needs-validation" novalidate @submit.prevent="submitForm">
      <h5 class="mb-4"><i class="bi bi-person-plus"></i> Create Account</h5>

      <!-- Error Alert -->
      <div v-if="error" class="alert alert-danger">{{ error }}</div>

      <!-- Name -->
      <div class="mb-4">
        <label>Name <span class="text-danger">*</span></label>
        <input
          v-model="form.name"
          type="text"
          class="form-control"
          :class="{ 'is-invalid': submitted && !validName }"
          placeholder="Enter Full Name"
          required
        />
        <div class="invalid-feedback">Name must contain only letters and spaces.</div>
      </div>

      <!-- Email -->
      <div class="mb-4">
        <label>Email <span class="text-danger">*</span></label>
        <input
          v-model="form.email"
          type="email"
          class="form-control"
          :class="{ 'is-invalid': submitted && !validEmail }"
          placeholder="Enter Email Address"
          required
        />
        <div class="invalid-feedback">Enter a valid email address.</div>
      </div>

      <!-- Mobile -->
      <div class="mb-4">
        <label>Mobile <span class="text-danger">*</span></label>
        <input
          v-model="form.mobile"
          type="text"
          class="form-control"
          :class="{ 'is-invalid': submitted && !validMobile }"
          placeholder="Enter Mobile Number"
          required
        />
        <div class="invalid-feedback">Mobile must be 10 digits and start with 6-9.</div>
      </div>

      <!-- Password -->
      <div class="mb-4">
        <label>Password <span class="text-danger">*</span></label>
        <input
          v-model="form.password"
          type="password"
          class="form-control"
          :class="{ 'is-invalid': submitted && !validPassword }"
          placeholder="Create a password"
          required
        />
        <div class="invalid-feedback">
          Password must be at least 8 characters, include uppercase and lowercase letters, a number, and a special character.
        </div>
      </div>



      <!-- Address -->
      <div class="mb-4">
        <label>Address <span class="text-danger">*</span></label>
        <textarea
          v-model="form.address"
          type="text"
          class="form-control"
          :class="{ 'is-invalid': submitted && !form.address }"
          placeholder="Enter Your address"
          required
        ></textarea>
        <div class="invalid-feedback">Address is required.</div>
      </div>

      <!-- Submit Button -->
      <button type="submit" class="btn btn-primary w-100">Create Account →</button>
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
        <div class="modal-content text-center">
          <div class="modal-header">
            <h5 class="modal-title" id="successModalLabel">Account Created</h5>
          </div>
          <div class="modal-body">
            <p>Your account was created successfully!</p>
            <p class="fw-bold">Redirecting to login in {{ countdown }} seconds...</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuth } from '@/composables/useAuth.js';
import { onMounted, ref } from 'vue';


export default {
  data() {
    return {
      form: {
        name: '',
        email: '',
        mobile: '',
        password: '',
        address: ''
      },
      submitted: false,
      error: '',
      countdown: 5
    };
  },
  computed: {
    validEmail() {
      return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.form.email);
    },
    validMobile() {
      return /^[6-9]\d{9}$/.test(this.form.mobile);
    },
    validName() {
      return /^[A-Za-z ]+$/.test(this.form.name);
    },
    validPassword() {
    return /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z\d]).{8,}$/.test(this.form.password);
  }
  },
  setup() {
    const { register } = useAuth();
    return { register };
  },

  methods: {
    async submitForm() {
      this.submitted = true;
      this.error = '';

      if (
        this.validName &&
        this.validEmail &&
        this.validMobile &&
        this.validPassword &&
        this.form.address
      ) {
        try {
          const res = await this.register(this.form);
          if (res.isSuccess) {
            this.showSuccessPopup();
          } else {
            this.error = res.message || 'Registration failed.';
          }
        } catch (err) {
          this.error = err.message || 'Something went wrong.';
        }
      }
    },
    showSuccessPopup() {
      const modal = new bootstrap.Modal(this.$refs.successModal);
      modal.show();
      const interval = setInterval(() => {
        this.countdown--;
        if (this.countdown === 0) {
          clearInterval(interval);
          modal.hide();
          this.$router.push('/auth?tab=login');
        }
      }, 1000);
    }
  }
};
</script>