<template>
  <div class="border rounded-4 p-3 shadow-sm mb-4">
    <h5 class="fw-bold">Quick Actions</h5>
    <div class="d-grid gap-2 mt-3">
      <button
        v-for="action in actions"
        :key="action.label"
        class="btn btn-outline-dark text-start"
        @click="$router.push(action.to)"
      >
        {{ action.label }}
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({ actions: Array });
</script>
