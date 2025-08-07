<template>
  <div
    v-if="role !== 'ADMIN'"
    class="border rounded-4 p-3 shadow-sm"
    >
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h5 class="fw-bold">Your Loan Applications</h5>
      <button class="btn btn-primary rounded-pill" @click="$router.push('/apply')">
        + New Application
      </button>
    </div>
    <LoanApplicationCard
      v-for="loan in loanApplications"
      :key="loan.title"
      v-bind="loan"
    />
  </div>

  <LoanApplicationCard 
    v-else
    v-for="loan in loanApplications"
    :key="loan.title"
    v-bind="loan"
  />
</template>

<script setup>
  import { ref } from 'vue';
  import LoanApplicationCard from './LoanApplicationCard.vue';

  defineProps({ loanApplications: Array });

  const role = ref(localStorage.getItem('userRole'));
</script>
