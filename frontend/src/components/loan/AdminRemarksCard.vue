<template>
  <div class="card mb-4">
    <div class="card-body">
      <h5><i class="bi bi-chat-left-text"></i> Admin Remarks</h5>
      <p class="text-muted" v-if="formattedReviewedDate">Reviewed At: {{ formattedReviewedDate }}</p>

      <div v-if="role === 'ADMIN' && loan.status !== 'APPROVED' && loan.status !== 'REJECTED'">
        <textarea
          v-model="remarks"
          class="form-control mb-3"
          rows="3"
          placeholder="Enter remarks"
        ></textarea>

        <!-- First Line: Under Review -->
        <div class="d-flex mb-2">
          <button class="btn bg-info w-100" @click="markUnderReview">Mark as Under Review</button>
        </div>

        <!-- Second Line: Reject and Approve -->
        <div class="d-flex gap-2">
          <button class="btn btn-danger w-50" @click="reject">Reject</button>
          <button class="btn btn-success w-50" @click="approve">Approve</button>
        </div>
      </div>

      <template v-else>
        <p v-if="loan.reviewRemarks && loan.reviewRemarks.trim() !== ''">{{ loan.reviewRemarks }}</p>
        <p v-else class="text-muted">No remarks</p>
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useAdmin } from '@/composables/useAdmin';

const props = defineProps(['loan', 'formattedReviewedDate']);
const emit = defineEmits(['updated']);

const role = localStorage.getItem('userRole');
const remarks = ref(props.loan.reviewRemarks || '');

const { changeLoanStatus } = useAdmin();

const updateStatus = async (status) => {
  const response = await changeLoanStatus(props.loan.id, status, remarks.value);
  if (response.isSuccess) {
    emit('updated');
  } else {
    alert(`Error updating loan to ${status}.`);
  }
};

const approve = () => updateStatus('APPROVED');
const reject = () => updateStatus('REJECTED');
const markUnderReview = () => updateStatus('UNDER_REVIEW');
</script>
