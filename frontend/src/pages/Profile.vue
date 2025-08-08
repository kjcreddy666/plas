<template>
  <div class="min-vh-100 bg-light">
    <div class="container py-4">
      <!-- Loading State -->
      <div v-if="isLoading" class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">
        <div class="text-center">
          <div class="spinner-border text-primary mb-3" role="status" style="width: 3rem; height: 3rem;">
            <span class="visually-hidden">Loading...</span>
          </div>
          <h5 class="text-muted">Loading your profile...</h5>
        </div>
      </div>

      <!-- Profile Content -->
      <div v-else-if="userProfile.name || userProfile.email">
        <!-- Page Header -->
        <div class="row mb-4">
          <div class="col-12">
            <div class="d-flex align-items-center mb-3">
              <button class="btn btn-link text-primary p-0 me-3" @click="$router.back()">
                <i class="bi bi-arrow-left fs-4"></i>
              </button>
              <div>
                <h1 class="h2 fw-bold mb-1">My Profile</h1>
                <p class="text-muted mb-0">View your personal information</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Profile Cards -->
        <div class="row g-4">
          <!-- Profile Summary Card -->
          <div class="col-lg-4">
            <div class="card border-0 shadow-sm h-100">
              <div class="card-body text-center p-4">
                <!-- Profile Avatar -->
                <div class="mb-4">
                  <div class="bg-primary rounded-circle d-inline-flex align-items-center justify-content-center mx-auto" 
                       style="width: 100px; height: 100px;">
                    <i class="bi bi-person-fill text-white" style="font-size: 2.5rem;"></i>
                  </div>
                </div>
                
                <!-- User Info -->
                <h4 class="card-title fw-bold mb-2">{{ userProfile.name || 'User Name' }}</h4>
                <p class="text-muted mb-3">{{ userProfile.email || 'user@example.com' }}</p>
                
                <!-- Role Badge -->
                <span class="badge bg-success rounded-pill px-3 py-2 mb-4">
                  <i class="bi bi-shield-check me-1"></i>
                  {{ userProfile.role || 'CUSTOMER' }}
                </span>

                <!--- Quick Stats - Only visible for Customer users -->
                <div v-if="userProfile.role === 'CUSTOMER'" class="border-top pt-4 mt-4">
                  <div class="row text-center">
                    <div class="col-6">
                      <div class="border-end">
                        <h5 class="fw-bold text-primary mb-1">{{ userStats.totalLoans }}</h5>
                        <small class="text-muted">Loans</small>
                      </div>
                    </div>
                    <div class="col-6">
                      <h5 class="fw-bold text-info mb-1">{{ userStats.totalTickets }}</h5>
                      <small class="text-muted">Tickets</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Personal Information Card -->
          <div class="col-lg-8">
            <div class="card border-0 shadow-sm h-100">
              <!-- Card Header -->
              <div class="card-header bg-white border-bottom py-3">
                <div class="d-flex justify-content-between align-items-center">
                  <h5 class="card-title mb-0 fw-bold">
                    <i class="bi bi-person-lines-fill text-primary me-2"></i>
                    Personal Information
                  </h5>
                </div>
              </div>
              
              <!-- Card Body -->
              <div class="card-body p-4">
                <div class="row g-4">
                  <!-- Full Name -->
                  <div class="col-md-6">
                    <div class="form-floating">
                      <input type="text" class="form-control bg-light" :value="userProfile.name ? userProfile.name : 'Not provided'" readonly>
                      <label class="text-muted">
                        <i class="bi bi-person me-1"></i>
                        Full Name
                      </label>
                    </div>
                  </div>
                  
                  <!-- Email -->
                  <div class="col-md-6">
                    <div class="form-floating">
                      <input type="email" class="form-control bg-light" :value="userProfile.email ? userProfile.email : 'Not provided'" readonly>
                      <label class="text-muted">
                        <i class="bi bi-envelope me-1"></i>
                        Email Address
                      </label>
                    </div>
                  </div>
                  
                  <!-- Mobile -->
                  <div class="col-md-6">
                    <div class="form-floating">
                      <input type="tel" class="form-control bg-light" :value="userProfile.mobile ? userProfile.mobile : 'Not provided'" readonly>
                      <label class="text-muted">
                        <i class="bi bi-phone me-1"></i>
                        Mobile Number
                      </label>
                    </div>
                  </div>
                  
                  <!-- Address -->
                  <div class="col-12">
                    <div class="form-floating">
                      <textarea class="form-control bg-light" style="height: 100px" readonly>{{ userProfile.address ? userProfile.address : 'Not provided' }}</textarea>
                      <label class="text-muted">
                        <i class="bi bi-geo-alt me-1"></i>
                        Address
                      </label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- No Data Fallback -->
      <div v-else class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">
        <div class="text-center">
          <div class="alert alert-warning border-0 shadow-sm" style="max-width: 500px;">
            <div class="mb-3">
              <i class="bi bi-exclamation-triangle-fill text-warning" style="font-size: 3rem;"></i>
            </div>
            <h4 class="alert-heading">Profile Data Not Available</h4>
            <p class="mb-4">Unable to load your profile information. Please try logging in again.</p>
            <div class="d-flex gap-2 justify-content-center">
              <button class="btn btn-primary" @click="$router.push('/auth')">
                <i class="bi bi-box-arrow-in-right me-2"></i>
                Login Again
              </button>
              <button class="btn btn-outline-secondary" @click="$router.push('/dashboard')">
                <i class="bi bi-house me-2"></i>
                Go to Dashboard
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useLoan } from '@/composables/useLoan';
import { useTicket } from '@/composables/useTicket';

const router = useRouter();

// Composables
const { loans, fetchLoansByUser } = useLoan();
const { tickets, fetchTicketsByUser } = useTicket();

// Get user data from localStorage
const getCurrentUserId = () => {
  return localStorage.getItem('userId');
};

const getCurrentUserData = () => {
  return {
    id: localStorage.getItem('userId') || '',
    name: localStorage.getItem('userName') || '',
    email: localStorage.getItem('userEmail') || '',
    mobile: localStorage.getItem('userMobile') || '',
    address: localStorage.getItem('userAddress') || '',
    role: localStorage.getItem('userRole') || 'CUSTOMER'
  };
};

// Reactive data
const userProfile = ref({
  id: '',
  name: '',
  email: '',
  mobile: '',
  address: '',
  role: '',
  createdAt: null,
  updatedAt: null
});

const userStats = ref({
  totalLoans: 0,
  totalTickets: 0
});

const isLoading = ref(true);

// Methods
const loadProfile = async () => {
  console.log('=== PROFILE LOADING ===');
  
  // Check if user is logged in
  const userId = getCurrentUserId();
  const localData = getCurrentUserData();
  
  console.log('User ID:', userId);
  console.log('Local Storage Data:', localData);
  
  if (!userId) {
    console.log('No user ID found, redirecting to auth');
    router.push('/auth');
    return;
  }
  
  // Load from localStorage
  userProfile.value = {
    ...localData,
    createdAt: null,
    updatedAt: null
  };
  
  console.log('Profile loaded from localStorage:', userProfile.value);
  isLoading.value = false;
};

const loadUserStats = async () => {
  const userId = getCurrentUserId();
  const userRole = localStorage.getItem('userRole');
  
  // Only load stats for customer users
  if (!userId || userRole !== 'CUSTOMER') {
    userStats.value = {
      totalLoans: 0,
      totalTickets: 0
    };
    return;
  }
  
  try {
    await Promise.all([
      fetchLoansByUser(userId),
      fetchTicketsByUser(userId)
    ]);
    
    userStats.value = {
      totalLoans: loans.value?.length || 0,
      totalTickets: tickets.value?.length || 0
    };
  } catch (error) {
    console.error('Error loading user stats:', error);
  }
};

// Lifecycle
onMounted(async () => {
  console.log('Profile component mounted');
  console.log('=== CHECKING AUTHENTICATION ===');
  
  // Check authentication
  const userId = getCurrentUserId();
  const token = localStorage.getItem('userToken');
  
  console.log('User ID:', userId);
  console.log('Token exists:', !!token);
  
  if (!userId || !token) {
    console.log('User not authenticated, redirecting to auth');
    router.push('/auth');
    return;
  }
  
  // Load profile and stats
  await Promise.all([loadProfile(), loadUserStats()]);
});
</script>

<style scoped>
/* Font styling to match other pages with +3px */
body, .container, .card-body, .form-control, .btn, p, span, small, label {
  font-size: calc(1rem + 3px) !important;
}

h1, h2, h3, h4, h5, h6 {
  font-size: calc(var(--bs-font-size-base) + 3px) !important;
}

.h1 { font-size: calc(2.5rem + 3px) !important; }
.h2 { font-size: calc(2rem + 3px) !important; }
.h3 { font-size: calc(1.75rem + 3px) !important; }
.h4 { font-size: calc(1.5rem + 3px) !important; }
.h5 { font-size: calc(1.25rem + 3px) !important; }
.h6 { font-size: calc(1.1rem + 3px) !important; }

/* Minimal custom styling - mostly using Bootstrap */
.card {
  border: none !important;
  border-radius: 0.75rem !important;
  transition: all 0.3s ease;
}

.card:hover {
  transform: translateY(-2px);
  transition: transform 0.2s ease;
}

/* Form control spacing improvements */
.form-control {
  padding: 1rem 0.75rem !important;
  line-height: 1.6 !important;
  letter-spacing: 0.5px !important;
}

.form-floating > .form-control {
  padding: 1.625rem 0.75rem 0.625rem !important;
}

.form-floating > textarea.form-control {
  padding: 1.625rem 0.75rem 0.625rem !important;
  line-height: 1.5 !important;
}

.form-floating > label {
  padding: 1rem 0.75rem !important;
  line-height: 1.25 !important;
}

/* Additional spacing for readonly inputs */
.form-control[readonly] {
  background-color: #f8f9fa !important;
  border-color: #e9ecef !important;
  padding-left: 1rem !important;
  padding-right: 1rem !important;
}

/* Form floating improvements */
.form-floating > .form-control:focus ~ label,
.form-floating > .form-control:not(:placeholder-shown) ~ label {
  opacity: 0.65;
  transform: scale(0.85) translateY(-0.5rem) translateX(0.15rem);
}

/* Button improvements */
.btn {
  transition: all 0.2s ease;
}

.btn:hover {
  transform: translateY(-1px);
}
</style>
