<template>
  <div class="card mb-4">
    <div class="card-body text-center status-card">
      <h6>
        <i :class="getLoanStatusIcon(loan.status)" class="me-1"></i>
        Application Status
      </h6>

      <span :class="['badge', getLoanStatusClass(loan.status)]">
        {{ loan.status }}
      </span>

      <hr />

      <p><strong>Submitted:</strong> {{ loan.applicationDate }}</p>
      <p v-if="formattedReviewedDate">
        <strong>Last Updated:</strong> {{ formattedReviewedDate }}
      </p>
    </div>
  </div>
</template>

<script setup>
defineProps(['loan', 'formattedReviewedDate']);

// Dynamic class for badge background color
const getLoanStatusClass = (status) => {
  switch (status) {
    case 'APPROVED':
      return 'text-bg-success';
    case 'NEW':
      return 'text-bg-warning';
    case 'UNDER_REVIEW':
      return 'text-bg-info';
    case 'REJECTED':
      return 'text-bg-danger';
    default:
      return 'text-bg-secondary';
  }
};

// Dynamic class for icon
const getLoanStatusIcon = (status) => {
  switch (status) {
    case 'APPROVED':
      return 'bi bi-check-circle-fill text-success';
    case 'NEW':
      return 'bi bi-hourglass-split text-warning';
    case 'UNDER_REVIEW':
      return 'bi bi-search text-info';
    case 'REJECTED':
      return 'bi bi-x-circle-fill text-danger';
    default:
      return 'bi bi-question-circle-fill text-secondary';
  }
};
</script>

<style scoped>
.badge {
  font-size: 0.9rem;
  padding: 0.5em 1em;
  border-radius: 0.5rem;
}
</style>
