<template>
  <div
    v-if="role !== 'ADMIN'"
    class="border rounded-4 p-3 shadow-sm"
    >
    <div class="d-flex justify-content-between align-items-center mb-3">
      <h5 class="fw-bold">Your Loan Applications</h5>
      <button class="btn btn-primary rounded-pill" @click="$router.push('/apply')">
        + New Application
      </button>
    </div>
    <LoanApplicationCard
      v-for="loan in loanApplications"
      :key="loan.title"
      v-bind="loan"
    />
  </div>

  <!-- Admin view -->
  <div v-else>
    <!-- Show message when no filters are selected -->
    <div v-if="noFiltersSelected" class="text-center py-5">
      <div class="text-muted mb-3">
        <i class="bi bi-funnel fs-1 text-secondary"></i>
      </div>
      <h5 class="text-muted">Please select a filter</h5>
      <p class="text-muted">Choose one or more status filters above to view loan applications.</p>
    </div>

    <!-- Show message when no loans match the filter -->
    <div v-else-if="loanApplications.length === 0" class="text-center py-5">
      <div class="text-muted mb-3">
        <i class="bi bi-inbox fs-1 text-secondary"></i>
      </div>
      <h5 class="text-muted">No loan applications found</h5>
      <p class="text-muted">No loan applications match the selected filter criteria.</p>
    </div>

    <!-- Show loan applications -->
    <div v-else>
      <LoanApplicationCard
        v-for="loan in loanApplications"
        :key="loan.title"
        v-bind="loan"
      />
    </div>
  </div>
</template>

<script setup>
  import { ref, computed } from 'vue';
  import LoanApplicationCard from './LoanApplicationCard.vue';

  const props = defineProps({
    loanApplications: Array,
    selectedStatuses: {
      type: Array,
      default: () => []
    }
  });

  const role = ref(localStorage.getItem('userRole'));

  // Check if no filters are selected for admin users
  const noFiltersSelected = computed(() => {
    return role.value === 'ADMIN' && (!props.selectedStatuses || props.selectedStatuses.length === 0);
  });
</script>
