<template>
  <div class="border rounded-4 p-3 shadow-sm">
    <div class="d-flex justify-content-between align-items-center">
      <h5 class="fw-bold">Recent Support</h5>
      <router-link
        v-if="tickets.length > 2"
        to="/support-ticket"
        class="btn btn-sm btn-link"
      >
        View More
      </router-link>
    </div>

    <div class="mt-3">
      <SupportTicket
      class="cursor-pointer"
        v-for="ticket in limitedTickets"
        :key="ticket.subject"
        v-bind="ticket"
        @click="$router.push(`/ticket/${ticket.ticketId}`)"
      />
    </div>
  </div>
</template>

<script setup>
import SupportTicket from './SupportTicket.vue';
import { computed } from 'vue';
const props = defineProps({ tickets: Array });

// Show only first 2 tickets
const limitedTickets = computed(() => props.tickets.slice(0, 2));
</script>
