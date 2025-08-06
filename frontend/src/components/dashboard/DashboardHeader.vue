<template>
    <div class="mb-4">
      <h1 class="fw-bold">Welcome back, {{ user.name }}!</h1>
      <p class="text-muted">{{ user.message }}</p>
    </div>
</template>
  
<script setup>
defineProps({ user: Object });
</script>  