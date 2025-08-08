<template>
  <div class="container-fluid py-4 min-vh-100">
    <h2 class="fw-bold">Customer Support</h2>
    <p class="text-muted">
      Get help with your loan application or account. Our support team is here to assist you.
    </p>

    <ul class="nav nav-tabs my-4">
      <li class="nav-item">
        <button class="nav-link" :class="{ active: tab === 'tickets' }" @click="tab = 'tickets'">
          <i class="bi bi-chat-dots me-1"></i> My Tickets
        </button>
      </li>
      <li class="nav-item">
        <button class="nav-link" :class="{ active: tab === 'new' }" @click="tab = 'new'">
          <i class="bi bi-plus-lg me-1"></i> New Ticket
        </button>
      </li>
    </ul>

    <div v-if="tab === 'tickets'">
      <TicketList />
    </div>
    <div v-else>
      <NewTicket />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import TicketList from '@/components/support/TicketList.vue'
import NewTicket from '@/components/support/NewTicket.vue'

const tab = ref('tickets')

const router = useRouter()
const userId = ref(localStorage.getItem('userId'))
const token = ref(localStorage.getItem('userToken'))
const role = ref(localStorage.getItem('userRole'))

onMounted(() => {
  if (!token.value || !userId.value || !role.value) {
    router.push('/auth')
  } else if (role.value !== 'CUSTOMER') {
    router.push('/unauthorized')
  }
})
</script>
