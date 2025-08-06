<template>
    <div>
      <QuickActions :actions="quickActions" />
      <RecentSupport :tickets="supportTickets" />
    </div>
  </template>
  
  <script setup>
  import QuickActions from './QuickActions.vue';
  import RecentSupport from './RecentSupport.vue';
  
  defineProps({
    quickActions: Array,
    supportTickets: Array,
  });
  </script>
  