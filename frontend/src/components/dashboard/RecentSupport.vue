<template>
    <div class="border rounded-4 p-3 shadow-sm">
      <h5 class="fw-bold">Recent Support</h5>
      <div class="mt-3">
        <SupportTicket
          v-for="ticket in tickets"
          :key="ticket.subject"
          v-bind="ticket"
          @click="$router.push(`/ticket/${ticket.ticketId}`)"
        />
      </div>
    </div>
  </template>
  
  <script setup>
  import SupportTicket from './SupportTicket.vue';
  defineProps({ tickets: Array });
  </script>
  