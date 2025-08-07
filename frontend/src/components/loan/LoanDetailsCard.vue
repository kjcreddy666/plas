<template>
    <div class="card mb-4">
      <div class="card-body">
        <h5><i class="bi bi-currency-dollar"></i> Loan Details</h5>
        <p><strong>Requested Amount:</strong> ${{ loan.amount.toLocaleString() }}</p>
        <p><strong>Purpose:</strong> {{ loan.purpose }}</p>
        <p><strong>Loan Term:</strong> {{ loan.tenureMonths }} months</p>
        <p v-if="loan.status === 'APPROVED'"><strong>Monthly Payment (Est.):</strong> ${{ loan.monthlyEstimate }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  defineProps(['loan']);
  </script>
  