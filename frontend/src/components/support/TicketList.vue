<template>
  <div class="border rounded-4 p-3" v-if="role === 'CUSTOMER'">
    <h5 class="mb-3 fw-bold">Your Support Tickets</h5>

    <div v-if="tickets.length === 0" class="text-muted">No tickets found.</div>

    <div
      v-for="ticket in tickets"
      :key="ticket.id"
      class="border rounded-3 p-3 mb-3"
      role="button"
      style="cursor: pointer"
      @click="goToTicket(ticket.id)"
    >
      <div class="d-flex justify-content-between align-items-start">
        <h6 class="fw-bold mb-1">{{ ticket.subject }}</h6>
        <span class="badge rounded-pill" :class="statusClass(ticket.status)">
          <i class="bi me-1" :class="statusIcon(ticket.status)"></i>
          {{ ticket.status }}
        </span>
      </div>
      <small class="text-muted">Created: {{ formatDate(ticket.createdAt) }}</small>
    </div>
  </div>

  <div
    v-else
    v-for="ticket in tickets"
    :key="ticket.id"
    class="border rounded-3 p-3 mb-3"
    role="button"
    style="cursor: pointer"
    @click="goToTicket(ticket.id)"
  >
    <div class="d-flex justify-content-between align-items-start">
      <h6 class="fw-bold mb-1">{{ ticket.subject }}</h6>
      <span class="badge rounded-pill" :class="statusClass(ticket.status)">
        <i class="bi me-1" :class="statusIcon(ticket.status)"></i>
        {{ ticket.status }}
      </span>
    </div>
    <small class="text-muted">Created: {{ formatDate(ticket.createdAt) }}</small>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useTicket } from '@/composables/useTicket';
import { useAdmin } from '@/composables/useAdmin';

const router = useRouter();

const userId = localStorage.getItem('userId');
const role = localStorage.getItem('userRole');

let tickets;
let fetch;

if (role === 'ADMIN') {
  const admin = useAdmin();
  tickets = admin.tickets;
  fetch = admin.fetchTickets;
} else {
  const ticket = useTicket();
  tickets = ticket.tickets;
  fetch = () => ticket.fetchTicketsByUser(userId);
}

onMounted(() => {
  if (userId) fetch();
});

const goToTicket = (id) => {
  router.push(`/ticket/${id}`);
};

const formatDate = (datetime) => {
  return new Date(datetime).toISOString().split('T')[0]; // YYYY-MM-DD
};

const statusClass = (status) => {
  return {
    OPEN: 'bg-warning-subtle text-warning',
    CLOSED: 'bg-danger-subtle text-danger',
    RESOLVED: 'bg-success-subtle text-success',
  }[status] || 'bg-secondary-subtle text-secondary';
};

const statusIcon = (status) => {
  return {
    OPEN: 'bi-exclamation-circle',
    CLOSED: 'bi-x-circle',
    RESOLVED: 'bi-check-circle',
  }[status] || 'bi-question-circle';
};
</script>
