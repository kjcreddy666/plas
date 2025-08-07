<template>
  <div class="card mb-4">
    <div class="card-body">
      <h5><i class="bi bi-person-fill"></i> Applicant Information</h5>
      <p><strong>Full Name: </strong> {{ userName }}</p>
      <p><strong>Email Address: </strong> {{ userEmail }}</p>
    </div>
  </div>
</template>

<script setup>
defineProps({
  loan: Object
});

const userName = localStorage.getItem('userName');
const userEmail = localStorage.getItem('userEmail');
</script>
