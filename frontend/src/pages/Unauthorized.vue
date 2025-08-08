<template>
  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6">
        <div class="card shadow-sm">
          <div class="card-body text-center py-5">
            <!-- Error Icon -->
            <div class="mb-4">
              <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="10" stroke="#dc3545" stroke-width="2" fill="none"/>
                <path d="M15 9l-6 6M9 9l6 6" stroke="#dc3545" stroke-width="2" stroke-linecap="round"/>
              </svg>
            </div>
            
            <!-- Error Code -->
            <h1 class="display-1 fw-bold text-danger mb-3">401</h1>
            
            <!-- Error Message -->
            <h3 class="fw-bold mb-3">Unauthorized Access</h3>
            <p class="text-muted mb-4">
              Sorry, you don't have permission to access this page. 
              Please log in with appropriate credentials or contact your administrator.
            </p>
            
            <!-- Action Buttons -->
            <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center">
              <router-link 
                to="/auth?tab=login" 
                class="btn text-white px-4 py-2"
                style="background-color: #7256FF;"
              >
                <i class="bi bi-box-arrow-in-right me-2"></i>
                Go to Login
              </router-link>
              <router-link 
                to="/" 
                class="btn btn-outline-secondary px-4 py-2"
              >
                <i class="bi bi-house me-2"></i>
                Back to Home
              </router-link>
            </div>
          </div>
        </div>
        
        <!-- Additional Help Section -->
        <div class="text-center mt-4">
          <p class="text-muted mb-2">Need help?</p>
          <router-link 
            to="/support" 
            class="text-decoration-none"
            style="color: #7256FF;"
          >
            Contact Support
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

// Optional: Add any logic for handling unauthorized access
// For example, you could add analytics tracking or logging here
</script>

<style scoped>
/* Custom styles for the unauthorized page */
.card {
  border: none;
  border-radius: 12px;
}

.btn {
  border-radius: 8px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

/* Custom hover effect for primary button */
.btn[style*="#7256FF"]:hover {
  background-color: #5a3fd8 !important;
}

/* Animation for the error icon */
svg {
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

/* Responsive adjustments */
@media (max-width: 576px) {
  .display-1 {
    font-size: 4rem;
  }
  
  .card-body {
    padding: 2rem 1rem;
  }
}
</style>
  