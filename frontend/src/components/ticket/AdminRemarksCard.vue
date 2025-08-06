<template>
  <div class="card mb-4">
    <div class="card-body">
      <h5><i class="bi bi-chat-left-text"></i> Admin Remarks</h5>
      <p class="text-muted" v-if="formattedReviewedDate">Reviewed At: {{ formattedReviewedDate }}</p>

      <div v-if="role === 'ADMIN' && !ticket.response">
        <textarea
          v-model="remarks"
          class="form-control mb-3"
          rows="3"
          placeholder="Enter response"
        ></textarea>
        <button class="btn btn-primary" @click="resolve">Resolve</button>
      </div>

      <template v-else>
        <p v-if="ticket.response && ticket.response.trim() !== ''">{{ ticket.response }}</p>
        <p v-else class="text-muted">No response given</p>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useAdmin } from '@/composables/useAdmin';

const props = defineProps(['ticket', 'formattedReviewedDate']);
const emit = defineEmits(['updated']);
const role = localStorage.getItem('userRole');
const remarks = ref(props.ticket.response || '');

const { resolveTicket } = useAdmin();

const resolve = async () => {
  const response = await resolveTicket(props.ticket.id, remarks.value);
  if (response.isSuccess) emit('updated');
  else alert('Error resolving ticket.');
};
</script>
