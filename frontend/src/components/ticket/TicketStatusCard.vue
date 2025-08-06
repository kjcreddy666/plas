<template>
  <div class="card mb-4">
    <div class="card-body text-center status-card">
      <h6 class="mb-3">
        <i :class="getTicketStatusIcon(ticket.status)" class="me-1"></i>
        Ticket Status
      </h6>

      <span :class="['badge', getTicketStatusClass(ticket.status)]">
        {{ ticket.status }}
      </span>

      <hr />

      <p><strong>Submitted:</strong> {{ formatDate(ticket.createdAt) }}</p>
      <p v-if="formattedReviewedDate">
        <strong>Last Updated:</strong> {{ formattedReviewedDate }}
      </p>
    </div>
  </div>
</template>

<script setup>
defineProps(['ticket', 'formattedReviewedDate']);

const formatDate = (val) => {
  if (!val) return 'Unknown';
  return new Date(val).toISOString().split('T')[0]; // e.g. 2025-08-06
};

// Badge class based on status
const getTicketStatusClass = (status) => {
  switch ((status || '').toUpperCase()) {
    case 'OPEN':
      return 'text-bg-warning';
    case 'CLOSED':
      return 'text-bg-danger';
    case 'RESOLVED':
      return 'text-bg-success';
    default:
      return 'text-bg-secondary';
  }
};

// Icon based on status
const getTicketStatusIcon = (status) => {
  switch ((status || '').toUpperCase()) {
    case 'OPEN':
      return 'bi bi-exclamation-circle-fill text-warning';
    case 'CLOSED':
      return 'bi bi-x-circle-fill text-danger';
    case 'RESOLVED':
      return 'bi bi-check-circle-fill text-success';
    default:
      return 'bi bi-question-circle-fill text-secondary';
  }
};
</script>
