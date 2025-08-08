<template>
    <div class="card mb-4">
      <div class="card-body">
        <h5><i class="bi bi-file-earmark-text"></i> Customer Submitted Details</h5>
        <p><strong>Income:</strong> ₹{{ loan.income  }}</p>
        <p><strong>Credit Score:</strong> {{ loan.creditScore }}</p>
      </div>
    </div>
  </template>
  
  <script setup>
  defineProps(['loan']);
  </script>
  