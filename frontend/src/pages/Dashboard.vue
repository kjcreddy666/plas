<template>
    <component :is="dashboardComponent" />
  </template>
  
<script setup>
  import { computed } from 'vue';
  import AdminDashboard from './AdminDashboard.vue';
  import UserDashboard from './UserDashboard.vue';
  
  const role = localStorage.getItem('userRole');
  
  const dashboardComponent = computed(() => {
    if (role === 'ADMIN') return AdminDashboard;
    return UserDashboard;
  });
</script>
  