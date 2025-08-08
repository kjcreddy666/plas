<template>
    <div class="border rounded-3 p-2 mb-2 d-flex justify-content-between align-items-center" @click="$emit('click')" >
      <div>
        <div class="fw-semibold">{{ subject }}</div>
        <small class="text-muted">{{ date }}</small>
      </div>
      <span :class="'badge ' + statusClass">{{ status }}</span>
    </div>
  </template>
  
  <script setup>
  defineProps({
    subject: String,
    date: String,
    status: String,
    statusClass: String,
  });
  </script>
  