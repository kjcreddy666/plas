<template>
  <div class="container my-5 min-vh-100">
    <button class="btn btn-link mb-3" @click="$router.back()">&larr; Back</button>
    <h3><strong>Loan Application Details</strong></h3>
    <p class="text-muted" v-if="loan">Application ID: {{ loan.id }}</p>
    <p class="text-muted" v-if="loan && loan.monthlyInstallment">
      Monthly Installment: ₹{{ loan.monthlyInstallment }}
    </p>

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
          <AdminRemarksCard
            :loan="loan"
            :formattedReviewedDate="formattedReviewedDate"
            @updated="refreshLoan"
          />
          <QuickActions v-if="role === 'CUSTOMER'" />
        </div>
      </div>

      <div v-if="loan.status === 'APPROVED' && role === 'CUSTOMER'">
        <RepaymentScheduleTable :schedule="schedule" />

        <div class="mt-3 d-flex justify-content-between align-items-center">
          <button
            class="btn btn-outline-primary"
            :disabled="currentPage === 0"
            @click="prevPage"
          >
            &larr; Previous
          </button>

          <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>

          <button
            class="btn btn-outline-primary"
            :disabled="currentPage + 1 >= totalPages"
            @click="nextPage"
          >
            Next &rarr;
          </button>
        </div>
      </div>
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
const {
  schedule,
  totalItems,
  currentPage,
  pageSize,
  fetchRepaymentSchedule
} = useEmi();

const totalPages = computed(() =>
  Math.ceil(totalItems.value / pageSize.value)
);

function formatDateOnly(dateStr) {
  const date = new Date(dateStr);
  return isNaN(date.getTime()) ? null : date.toISOString().split('T')[0];
}

const formattedReviewedDate = computed(() =>
  loan.value?.reviewedAt ? formatDateOnly(loan.value.reviewedAt) : null
);

const refreshLoan = async () => {
  const id = route.params.id;
  if (!id) return;

  await fetchLoanById(id);

  if (loan.value && loan.value.status === 'APPROVED') {
    const reviewedDate = loan.value.reviewedAt
      ? new Date(loan.value.reviewedAt)
      : new Date();
    const startMonth = reviewedDate.getMonth() + 2;

    await fetchRepaymentSchedule(loan.value.id, 12, 0, pageSize.value);

    // Set monthly estimate from first schedule entry
    if (schedule.value && schedule.value.length > 0) {
      loan.value.monthlyInstallment = schedule.value[0].totalAmount;
    }
  }
};

const nextPage = async () => {
  if (currentPage.value + 1 < totalPages.value) {
    await fetchRepaymentSchedule(loan.value.id, 12, currentPage.value + 1, pageSize.value);
  }
};

const prevPage = async () => {
  if (currentPage.value > 0) {
    await fetchRepaymentSchedule(loan.value.id, 12, currentPage.value - 1, pageSize.value);
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
