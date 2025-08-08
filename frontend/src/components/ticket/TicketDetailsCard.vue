<template>
  <div class="card mb-4">
    <div class="card-body">
      <h5><strong>Ticket Details</strong></h5>

      <p><strong>Ticket ID:</strong> {{ ticket?.id }}</p>

      <p>
        <strong>Linked Loan ID:</strong>
        <router-link :to="`/loans/${ticket?.loanId}`" class="text-decoration-none">
          {{ ticket?.loanId }} <span class="ms-1">↗</span>
        </router-link>
      </p>

      <p><strong>Subject:</strong> {{ ticket?.subject }}</p>
      <p><strong>Description:</strong> {{ ticket?.description }}</p>
      <p><strong>Created At:</strong> {{ new Date(ticket?.createdAt).toLocaleString() }}</p>
      <p v-if="ticket?.status !== 'OPEN'"><strong>Last Updated:</strong> {{ new Date(ticket?.updatedAt).toLocaleString()}}</p>
    </div>
  </div>
</template>

<script setup>
defineProps(['ticket']);
</script>
