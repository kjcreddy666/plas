<template>
    <section id="financial-future" class="container-fluid vh-100">
      <div class="row h-100">
        <div class="col-6 text-black d-flex align-items-center justify-content-start">
          <div class="text-start px-5" style="max-width: 700px;">
            <h1 class="display-1 fw-bold">Your Financial Future,</h1>
            <h1 class="display-1 fw-bold" style="color: #7256FF;">Simplified</h1>
            <p class="mt-3">
              Transform your loan application experience with our intelligent platform. Get instant EMI calculations, track your application status, and receive dedicated support throughout your journey.
            </p>
            <div class="mt-4 d-flex gap-3">
              <button class="btn text-white" style="background-color: #7256FF;" @click="goToSignUp()">Get Started</button>
              <button class="btn btn-outline-dark" @click="goToEMI()">Try EMI Calculator</button>
            </div>
          </div>
        </div>
        <div class="col-6 d-flex align-items-center justify-content-center">
          <div class="illustration-container">
            <img 
              src="@/assets/images/loan-illustration.svg" 
              alt="Loan Application Illustration" 
              class="img-fluid loan-illustration"
              style="max-width: 625px; height: auto;"
            />
          </div>
        </div>
      </div>
    </section>
  </template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();
function goToEMI() {
  router.push('/emi-calculator');
}
function goToSignUp() {
  router.push('/auth?tab=register');
}
</script>

<style scoped>
.illustration-container {
  position: relative;
  padding: 2rem;
  perspective: 1000px;
}

.loan-illustration {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
  filter: drop-shadow(0 15px 35px rgba(114, 86, 255, 0.15));
  transform-style: preserve-3d;
}

.loan-illustration:hover {
  transform: scale(1.08) rotateY(5deg) rotateX(2deg);
  filter: drop-shadow(0 25px 50px rgba(114, 86, 255, 0.25));
}

/* Text animations */
.display-1 {
  animation: slideInLeft 1s ease-out;
}

.display-1:nth-child(2) {
  animation: slideInLeft 1s ease-out 0.2s both;
}

p {
  animation: fadeInUp 1s ease-out 0.4s both;
}

.btn {
  animation: fadeInUp 1s ease-out 0.6s both;
  transition: all 0.3s ease;
}

.btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 25px rgba(114, 86, 255, 0.3);
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .loan-illustration {
    max-width: 100% !important;
  }
  
  .illustration-container {
    padding: 1rem;
  }
  
  .loan-illustration:hover {
    transform: scale(1.05);
  }
}

/* Enhanced floating animation */
@keyframes float {
  0%, 100% {
    transform: translateY(0px) rotate(0deg);
  }
  25% {
    transform: translateY(-8px) rotate(1deg);
  }
  50% {
    transform: translateY(-15px) rotate(0deg);
  }
  75% {
    transform: translateY(-8px) rotate(-1deg);
  }
}

.loan-illustration {
  animation: float 4s ease-in-out infinite, fadeInRight 1s ease-out;
}

/* Text slide animations */
@keyframes slideInLeft {
  from {
    opacity: 0;
    transform: translateX(-50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInRight {
  from {
    opacity: 0;
    transform: translateX(50px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

/* Pulse effect for buttons */
@keyframes pulse {
  0% {
    box-shadow: 0 0 0 0 rgba(114, 86, 255, 0.7);
  }
  70% {
    box-shadow: 0 0 0 10px rgba(114, 86, 255, 0);
  }
  100% {
    box-shadow: 0 0 0 0 rgba(114, 86, 255, 0);
  }
}

.btn[style*="#7256FF"] {
  animation: fadeInUp 1s ease-out 0.6s both, pulse 2s infinite 2s;
}

/* Staggered animation for button group */
.btn:nth-child(1) {
  animation-delay: 0.6s;
}

.btn:nth-child(2) {
  animation-delay: 0.8s;
}

/* Add shine effect */
@keyframes shine {
  0% {
    background-position: -200% center;
  }
  100% {
    background-position: 200% center;
  }
}

.loan-illustration::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    45deg,
    transparent 30%,
    rgba(255, 255, 255, 0.1) 50%,
    transparent 70%
  );
  background-size: 200% 200%;
  animation: shine 3s ease-in-out infinite 1s;
  pointer-events: none;
}
</style>