<template>
  <router-link
    :to="`/loans/${id}`"
    class="text-decoration-none text-dark"
  >
    <div class="border rounded-3 p-3 mb-3">
      <h6 class="fw-bold mb-1">{{ title }}</h6>
      <small class="text-muted">Applied on {{ appliedOn }}</small>

      <div class="d-flex justify-content-between align-items-center mt-3">
        <!-- Amount (Left-aligned) -->
        <div>
          Amount<br />
          <strong>${{ amount.toLocaleString() }}</strong>
        </div>

        <!-- Term (Center) -->
        <div class="text-center">
          Term<br />
          <strong>{{ term }} months</strong>
        </div>

        <!-- Status (Right-aligned but text centered inside) -->
        <div
          class="d-flex flex-column align-items-end"
          style="min-width: 120px;"
        >
          <span class="mb-1" style="text-align: center; width: 100%;">Status</span>
          <span
            :class="'badge ' + statusClass"
            style="display: inline-block; width: 120px; text-align: center;"
          >
            {{ status }}
          </span>
        </div>
      </div>
    </div>
  </router-link>
</template>

<script setup>
defineProps({
  id: [String, Number],
  title: String,
  appliedOn: String,
  amount: Number,
  term: Number,
  status: String,
  statusClass: String,
});
</script>
