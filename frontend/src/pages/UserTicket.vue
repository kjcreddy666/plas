<template>
    <div class="container my-5">
      <button class="btn btn-link mb-3" @click="$router.back()">&larr; Back</button>
      <h3><strong>Ticket Details</strong></h3>
      <p class="text-muted" v-if="ticket">Application ID: {{ ticket.id }}</p>
  
      <div v-if="loading">Loading...</div>
      <div v-else-if="error" class="text-danger">Error: {{ error }}</div>
  
      <div v-else-if="ticket">
        <div class="d-flex justify-content-between align-items-stretch gap-4 flex-wrap">
          <div class="left-column">
            <TicketDetailsCard :ticket="ticket" />
          </div>
  
          <div class="right-column">
            <TicketStatusCard :ticket="ticket" :formattedReviewedDate="formattedReviewedDate" />
            <AdminRemarksCard
              :ticket="ticket"
              :formattedReviewedDate="formattedReviewedDate"
              @updated="refreshTicket"
            />
            <QuickActions v-if="role === 'CUSTOMER'" />
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  import { useRoute } from 'vue-router';
  import { useTicket } from '@/composables/useTicket';
  import { useEmi } from '@/composables/useEmi';
  
  import TicketDetailsCard from '../components/ticket/TicketDetailsCard.vue';
  import TicketStatusCard from '../components/ticket/TicketStatusCard.vue';
  import AdminRemarksCard from '../components/ticket/AdminRemarksCard.vue';
  
  const route = useRoute();
  const role = localStorage.getItem('userRole');
  
  const { ticket, loading, error, fetchTicketById } = useTicket();
  const { schedule, fetchRepaymentSchedule } = useEmi();
  
  function formatDateOnly(dateStr) {
    const date = new Date(dateStr);
    return isNaN(date.getTime()) ? null : date.toISOString().split('T')[0];
  }
  
  const formattedReviewedDate = computed(() =>
  ticket.value?.updatedAt ? formatDateOnly(ticket.value.updatedAt) : null
);

  
  const refreshTicket = async () => {
    const id = route.params.id;
    if (!id) return;
  
    await fetchTicketById(id);
  
    if (ticket.value && ticket.value.status === 'APPROVED') {
      const reviewedDate = ticket.value.reviewedAt
        ? new Date(ticket.value.reviewedAt)
        : new Date();
      const startMonth = reviewedDate.getMonth() + 2;
      await fetchRepaymentSchedule(ticket.value.id, 12, startMonth);
    }
  };
  
  onMounted(refreshTicket);
  </script>
  
  <style scoped>
  .left-column {
    width: 68%;
    display: flex;
    flex-direction: column;
  }
  .right-column {
    width: 30%;
    display: flex;
    flex-direction: column;
  }
  .card {
    border-radius: 1rem;
  }
  </style>
  