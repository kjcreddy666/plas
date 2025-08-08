<template>
  <div class="container py-5">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6">
        <div class="card shadow-sm">
          <div class="card-body text-center py-5">
            <!-- Error Icon -->
            <div class="mb-4">
              <svg width="80" height="80" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="12" r="10" stroke="#6c757d" stroke-width="2" fill="none"/>
                <path d="M9 9h6v6h-6z" stroke="#6c757d" stroke-width="2" fill="none"/>
                <circle cx="12" cy="17" r="1" fill="#6c757d"/>
              </svg>
            </div>
            
            <!-- Error Code -->
            <h1 class="display-1 fw-bold text-secondary mb-3">404</h1>
            
            <!-- Error Message -->
            <h3 class="fw-bold mb-3">Page Not Found</h3>
            <p class="text-muted mb-4">
              Sorry, the page you are looking for doesn't exist or has been moved. 
              Please check the URL or navigate back to our homepage.
            </p>
            
            <!-- Action Buttons -->
            <div class="d-flex flex-column flex-sm-row gap-3 justify-content-center">
              <router-link 
                to="/" 
                class="btn text-white px-4 py-2"
                style="background-color: #7256FF;"
              >
                <i class="bi bi-house me-2"></i>
                Go to Home
              </router-link>
              <button 
                @click="goBack"
                class="btn btn-outline-secondary px-4 py-2"
              >
                <i class="bi bi-arrow-left me-2"></i>
                Go Back
              </button>
            </div>
          </div>
        </div>
        
        <!-- Additional Help Section -->
        <div class="text-center mt-4">
          <p class="text-muted mb-2">Still can't find what you're looking for?</p>
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

// Function to go back to previous page
const goBack = () => {
  if (window.history.length > 1) {
    router.go(-1);
  } else {
    router.push('/');
  }
};
</script>

<style scoped>
/* Custom styles for the not found page */
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
  animation: bounce 2s infinite;
}

@keyframes bounce {
  0%, 20%, 50%, 80%, 100% {
    transform: translateY(0);
  }
  40% {
    transform: translateY(-10px);
  }
  60% {
    transform: translateY(-5px);
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
  