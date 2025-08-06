<template>
  <div class="container py-4">
    <DashboardHeader :user="user" />
    <StatsRow :stats="stats" />

    <!-- Tabs + Filter -->
    <div class="d-flex justify-content-between align-items-center mb-4">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a
            href="#"
            class="nav-link"
            :class="{ active: selectedTab === 'loans' }"
            @click.prevent="selectedTab = 'loans'"
          >
            Loan Applications
          </a>
        </li>
        <li class="nav-item">
          <a
            href="#"
            class="nav-link"
            :class="{ active: selectedTab === 'tickets' }"
            @click.prevent="selectedTab = 'tickets'"
          >
            Support Tickets
          </a>
        </li>
      </ul>

      <!-- Filter Dropdown for Loans -->
      <div v-if="selectedTab === 'loans'" class="dropdown">
        <button
          class="btn btn-outline-secondary dropdown-toggle"
          type="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          Filter Status
        </button>
        <ul class="dropdown-menu p-3" style="min-width: 200px;">
          <li v-for="status in availableLoanStatuses" :key="status">
            <div class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                :id="status"
                :value="status"
                v-model="selectedLoanStatuses"
                @change="applyLoanFilters"
              />
              <label class="form-check-label" :for="status">{{ status }}</label>
            </div>
          </li>
        </ul>
      </div>
    </div>

    <!-- Tab Content -->
    <div class="row g-4">
      <div class="col-md-12" v-if="selectedTab === 'loans'">
        <LoanApplications :loanApplications="loanApplications" />
      </div>
      <div class="col-md-12" v-else>
        <TicketList :tickets="supportTickets" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuthGuard } from '@/composables/useAuthGuard';
import { useAdmin } from '@/composables/useAdmin';

import DashboardHeader from '../components/dashboard/DashboardHeader.vue';
import StatsRow from '../components/dashboard/StatsRow.vue';
import LoanApplications from '../components/dashboard/LoanApplications.vue';
import TicketList from '@/components/support/TicketList.vue';

const route = useRoute();
const router = useRouter();
const { userId } = useAuthGuard();
const {
  loans,
  tickets,
  fetchAllLoans,
  fetchFilteredLoans,
  fetchTickets,
} = useAdmin();

const user = {
  name: 'ADMIN',
  message: 'Manage loan applications and support tickets across the platform.',
};

const stats = ref([
  { label: 'Total Applications', value: 0, class: 'fw-bold' },
  { label: 'Approved', value: 0, class: 'text-success fw-bold' },
  { label: 'Total Loan Amount', value: 0, class: 'text-warning fw-bold' },
  { label: 'Open Tickets', value: 0, class: 'fw-bold' },
]);

const loanApplications = ref([]);
const supportTickets = ref([]);
const selectedTab = ref('loans');

// Filter State
const availableLoanStatuses = ['NEW', 'UNDER_REVIEW', 'APPROVED', 'REJECTED'];
const selectedLoanStatuses = ref([...availableLoanStatuses]);

const applyLoanFilters = async () => {
  await fetchFilteredLoans(selectedLoanStatuses.value);

  loanApplications.value = loans.value.map((loan) => ({
    id: loan.loanId,
    title: loan.purpose,
    appliedOn: formatDate(loan.applicationDate),
    amount: loan.amount,
    term: loan.tenureMonths,
    status: loan.status,
    statusClass: getLoanStatusClass(loan.status),
  }));

  updateStats();
};

const updateStats = () => {
  const approvedCount = loans.value.filter((l) => l.status === 'APPROVED').length;
  const totalAmount = loans.value.reduce((sum, l) => sum + l.amount, 0);
  const openTickets = tickets.value.filter((t) => t.status === 'OPEN').length;

  stats.value = [
    { label: 'Total Applications', value: loans.value.length, class: 'fw-bold' },
    { label: 'Approved', value: approvedCount, class: 'text-success fw-bold' },
    { label: 'Total Loan Amount', value: totalAmount, class: 'text-warning fw-bold' },
    { label: 'Open Tickets', value: openTickets, class: 'fw-bold' },
  ];
};

// Sync tab from query on load
onMounted(async () => {
  const tabFromQuery = route.query.tab;
  if (tabFromQuery === 'tickets' || tabFromQuery === 'loans') {
    selectedTab.value = tabFromQuery;
  }

  // Sync filters from query
  if (route.query.statuses && tabFromQuery === 'loans') {
    const statusesFromQuery = route.query.statuses.split(',');
    selectedLoanStatuses.value = statusesFromQuery.filter(s =>
      availableLoanStatuses.includes(s)
    );
  }

  await fetchAllLoans();
  await fetchTickets();

  loanApplications.value = loans.value.map((loan) => ({
    id: loan.loanId,
    title: loan.purpose,
    appliedOn: formatDate(loan.applicationDate),
    amount: loan.amount,
    term: loan.tenureMonths,
    status: loan.status,
    statusClass: getLoanStatusClass(loan.status),
  }));

  supportTickets.value = tickets.value.map((ticket) => ({
    subject: ticket.subject,
    date: formatDate(ticket.createdAt),
    status: ticket.status,
    statusClass: getTicketStatusClass(ticket.status),
  }));

  updateStats();

  if (selectedTab.value === 'loans') {
    await applyLoanFilters();
  }
});

// Watch tab change → update URL and filters
watch(selectedTab, async (newTab) => {
  const query = { ...route.query, tab: newTab };

  if (newTab === 'loans') {
    query.statuses = selectedLoanStatuses.value.join(',');
    await applyLoanFilters();
  } else {
    delete query.statuses;
  }

  router.replace({ query });
});

// Watch filters → update URL
watch(selectedLoanStatuses, (newStatuses) => {
  if (selectedTab.value === 'loans') {
    router.replace({
      query: {
        ...route.query,
        tab: 'loans',
        statuses: newStatuses.join(','),
      },
    });
  }
});

// Helpers
const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return date.toLocaleDateString('en-US', {
    month: 'short',
    day: 'numeric',
    year: 'numeric',
  });
};

const getLoanStatusClass = (status) => {
  switch (status) {
    case 'APPROVED': return 'text-bg-success';
    case 'NEW': return 'text-bg-warning';
    case 'UNDER_REVIEW': return 'text-bg-info';
    case 'REJECTED': return 'text-bg-danger';
    default: return 'text-bg-secondary';
  }
};

const getTicketStatusClass = (status) => {
  switch (status) {
    case 'Resolved': return 'text-bg-success';
    case 'In Progress': return 'text-bg-primary';
    case 'Open': return 'text-bg-secondary';
    case 'Closed': return 'text-bg-dark';
    default: return 'text-bg-light';
  }
};
</script>

