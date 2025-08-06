<template>
    <div class="col-md-3">
      <div class="border rounded-4 p-3 text-center shadow-sm">
        <div class="text-muted">{{ label }}</div>
        <h4 :class="cssClass">{{ value }}</h4>
      </div>
    </div>
  </template>
  
  <script setup>
  defineProps({
    label: String,
    value: [String, Number],
    cssClass: String,
  });
  </script>
  