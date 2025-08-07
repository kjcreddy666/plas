<template>
    <div class="container-fluid vh-100">
      <div class="row h-100">
        <div class="col-6 text-black d-flex align-items-center justify-content-start">
          <div class="text-start px-5" style="max-width: 700px;">
            <h1 class="display-1 fw-bold">Your Financial Future,</h1>
            <h1 class="display-1 fw-bold" style="color: #7256FF;">Simplified</h1>
            <p class="mt-3">
              Transform your loan application experience with our intelligent platform. Get instant EMI calculations, track your application status, and receive dedicated support throughout your journey.
            </p>
            <div class="mt-4 d-flex gap-3">
              <button class="btn text-white" style="background-color: #7256FF;" @click="goToRegister">Get Started</button>
              <button class="btn btn-outline-dark" @click="goToEMI">Try EMI Calculator</button>
            </div>
          </div>
        </div>
        <div class="col-6 text-white d-flex align-items-center justify-content-center">
          <DotGrid></DotGrid>
        </div>
      </div>
    </div>
  </template>

<script setup>
import DotGrid from './DotGrid.vue';
import { useRouter } from 'vue-router';

const router = useRouter();
function goToEMI() {
  router.push('/emi-calculator');
}
function goToRegister() {
  router.push('/auth?tab=register');
}
</script>
