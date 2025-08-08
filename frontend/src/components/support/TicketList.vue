<template>
  <div class="border rounded-4 p-3" v-if="role === 'CUSTOMER'">
    <h5 class="mb-3 fw-bold">Your Support Tickets</h5>

    <div v-if="ticketsToDisplay.length === 0" class="text-muted">No tickets found.</div>

    <div
      v-for="ticket in ticketsToDisplay"
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

  <!-- Admin view -->
  <div v-else>
    <!-- Show message when no filters are selected -->
    <div v-if="noFiltersSelected" class="text-center py-5">
      <div class="text-muted mb-3">
        <i class="bi bi-funnel fs-1 text-secondary"></i>
      </div>
      <h5 class="text-muted">Please select a filter</h5>
      <p class="text-muted">Choose one or more status filters above to view tickets.</p>
    </div>

    <!-- Show message when no tickets match the filter -->
    <div v-else-if="ticketsToDisplay.length === 0" class="text-center py-5">
      <div class="text-muted mb-3">
        <i class="bi bi-inbox fs-1 text-secondary"></i>
      </div>
      <h5 class="text-muted">No tickets found</h5>
      <p class="text-muted">No tickets match the selected filter criteria.</p>
    </div>

    <!-- Show tickets -->
    <div v-else>
      <div
        v-for="ticket in ticketsToDisplay"
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
  </div>
</template>

<script setup>
import { onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useTicket } from '@/composables/useTicket';

// Props from parent component (AdminDashboard)
const props = defineProps({
  tickets: {
    type: Array,
    default: () => []
  },
  selectedStatuses: {
    type: Array,
    default: () => []
  }
});

const router = useRouter();

const userId = localStorage.getItem('userId');
const role = localStorage.getItem('userRole');

// For CUSTOMER role, use useTicket to fetch user's tickets
const customerTickets = role === 'CUSTOMER' ? useTicket() : null;

onMounted(() => {
  // Only fetch tickets if it's a customer (admin tickets are passed via props)
  if (role === 'CUSTOMER' && userId) {
    customerTickets.fetchTicketsByUser(userId);
  }
});

// Use appropriate tickets based on role
const ticketsToDisplay = computed(() => {
  if (role === 'CUSTOMER') {
    return customerTickets?.tickets?.value || [];
  } else {
    return props.tickets || [];
  }
});

// Check if no filters are selected for admin users
const noFiltersSelected = computed(() => {
  return role === 'ADMIN' && (!props.selectedStatuses || props.selectedStatuses.length === 0);
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
    CLOSED: 'bg-success-subtle text-success',
    RESOLVED: 'bg-info-subtle text-info',
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
