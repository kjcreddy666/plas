<template>
  <div class="container my-5">
    <button class="btn btn-link mb-3" @click="$router.back()">&larr; Back</button>
    <h3><strong>Loan Application Details</strong></h3>
    <p class="text-muted" v-if="loan">Application ID: {{ loan.id }}</p>

    <div v-if="loading">Loading...</div>
    <div v-else-if="error" class="text-danger">Error: {{ error }}</div>

    <div v-else-if="loan">
      <div class="d-flex justify-content-between align-items-stretch gap-4 flex-wrap">
        <div class="left-column">
          <LoanApplicantInfo :loan="loan" v-if="role === 'CUSTOMER'" />
          <LoanDetailsCard :loan="loan" />
          <CustomerSubmittedDetails :loan="loan" />
        </div>

        <div class="right-column">
          <LoanStatusCard :loan="loan" :formattedReviewedDate="formattedReviewedDate" />
          <!-- ✅ Added @updated event handler -->
          <AdminRemarksCard
            :loan="loan"
            :formattedReviewedDate="formattedReviewedDate"
            @updated="refreshLoan"
          />
          <QuickActions v-if="role === 'CUSTOMER'" />
        </div>
      </div>

      <RepaymentScheduleTable
        v-if="loan.status === 'APPROVED' && role === 'CUSTOMER'"
        :schedule="schedule"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import { useLoan } from '@/composables/useLoan';
import { useEmi } from '@/composables/useEmi';

import LoanApplicantInfo from '../components/loan/LoanApplicantInfo.vue';
import LoanDetailsCard from '../components/loan/LoanDetailsCard.vue';
import CustomerSubmittedDetails from '../components/loan/CustomerSubmittedDetails.vue';
import LoanStatusCard from '../components/loan/LoanStatusCard.vue';
import AdminRemarksCard from '../components/loan/AdminRemarksCard.vue';
import QuickActions from '../components/loan/QuickActions.vue';
import RepaymentScheduleTable from '../components/loan/RepaymentScheduleTable.vue';

const route = useRoute();
const role = localStorage.getItem('userRole');
const { loan, loading, error, fetchLoanById } = useLoan();
const { schedule, fetchRepaymentSchedule } = useEmi();

function formatDateOnly(dateStr) {
  const date = new Date(dateStr);
  return isNaN(date.getTime()) ? null : date.toISOString().split('T')[0];
}

const formattedReviewedDate = computed(() =>
  loan.value?.reviewedAt ? formatDateOnly(loan.value.reviewedAt) : null
);

// ✅ Refresh logic used both on mount and when updated
const refreshLoan = async () => {
  const id = route.params.id;
  if (!id) return;

  await fetchLoanById(id);

  if (loan.value && loan.value.status === 'APPROVED') {
    const reviewedDate = loan.value.reviewedAt
      ? new Date(loan.value.reviewedAt)
      : new Date();
    const startMonth = reviewedDate.getMonth() + 2;
    await fetchRepaymentSchedule(loan.value.id, 12, startMonth);
  }
};

onMounted(refreshLoan);
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
