<template>
  <div class="container-fluid py-4 min-vh-100">
    <DashboardHeader :user="user" />
    <StatsRow :stats="stats" />

    <!-- Tabs + Filter -->
    <div class="d-flex justify-content-between align-items-center mb-4">
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <a href="#" class="nav-link" :class="{ active: selectedTab === 'loans' }" @click.prevent="selectedTab = 'loans'">
            Loan Applications
          </a>
        </li>
        <li class="nav-item">
          <a href="#" class="nav-link" :class="{ active: selectedTab === 'tickets' }" @click.prevent="selectedTab = 'tickets'">
            Support Tickets
          </a>
        </li>
      </ul>

      <div>
        <div v-if="selectedTab === 'loans'" class="dropdown">
          <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
            Filter Status
          </button>
          <ul class="dropdown-menu p-3" style="min-width: 200px;">
            <li v-for="status in availableLoanStatuses" :key="status">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" :id="status" :value="status" v-model="selectedLoanStatuses" @change="applyLoanFilters" />
                <label class="form-check-label" :for="status">{{ status }}</label>
              </div>
            </li>
          </ul>
        </div>

        <div v-if="selectedTab === 'tickets'" class="dropdown">
          <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown">
            Filter Status
          </button>
          <ul class="dropdown-menu p-3" style="min-width: 200px;">
            <li v-for="status in availableTicketStatuses" :key="status">
              <div class="form-check">
                <input class="form-check-input" type="checkbox" :id="'ticket-' + status" :value="status" v-model="selectedTicketStatuses" @change="applyTicketFilters" />
                <label class="form-check-label" :for="'ticket-' + status">{{ status }}</label>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Tab Content -->
    <div class="row g-4">
      <div class="col-md-12" v-if="selectedTab === 'loans'">
        <LoanApplications :loanApplications="loanApplications" :selectedStatuses="selectedLoanStatuses" />
      </div>
      <div class="col-md-12" v-else>
        <TicketList :tickets="supportTickets" :selectedStatuses="selectedTicketStatuses" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
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
const { loans, tickets, fetchAllLoans, fetchTickets } = useAdmin();

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

const allLoans = ref([]);
const allTickets = ref([]);
const loanApplications = ref([]);
const supportTickets = ref([]);
const selectedTab = ref('loans');

const availableLoanStatuses = ['NEW', 'UNDER_REVIEW', 'APPROVED', 'REJECTED'];
const selectedLoanStatuses = ref([...availableLoanStatuses]);

const availableTicketStatuses = ['OPEN', 'RESOLVED', 'CLOSED'];
const selectedTicketStatuses = ref([...availableTicketStatuses]);

const applyLoanFilters = () => {
  const filtered = allLoans.value.filter(loan =>
    selectedLoanStatuses.value.includes(loan.status)
  );

  loanApplications.value = filtered.map(loan => ({
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

const applyTicketFilters = () => {
  const filtered = allTickets.value.filter(ticket =>
    selectedTicketStatuses.value.includes(ticket.status)
  );

  supportTickets.value = filtered.map(ticket => ({
    id: ticket.id,
    subject: ticket.subject,
    createdAt: ticket.createdAt,
    status: ticket.status,
    statusClass: getTicketStatusClass(ticket.status),
  }));

  updateStats();
};

const updateStats = () => {
  const approvedLoans = allLoans.value.filter(l => l.status === 'APPROVED');
  const approvedAmount = approvedLoans.reduce((sum, l) => sum + l.amount, 0);
  const openTickets = allTickets.value.filter(t => t.status === 'OPEN').length;

  stats.value = [
    { label: 'Total Applications', value: allLoans.value.length, class: 'fw-bold' },
    { label: 'Approved', value: approvedLoans.length, class: 'text-success fw-bold' },
    { label: 'Total Loan Amount', value: approvedAmount, class: 'text-warning fw-bold' },
    { label: 'Open Tickets', value: openTickets, class: 'fw-bold' },
  ];
};

onMounted(async () => {
  const tabFromQuery = route.query.tab;
  if (tabFromQuery === 'tickets' || tabFromQuery === 'loans') {
    selectedTab.value = tabFromQuery;
  }

  if (route.query.statuses && selectedTab.value === 'loans') {
    selectedLoanStatuses.value = route.query.statuses
      .split(',')
      .filter(s => availableLoanStatuses.includes(s));
  }

  if (route.query.ticketStatuses && selectedTab.value === 'tickets') {
    selectedTicketStatuses.value = route.query.ticketStatuses
      .split(',')
      .filter(s => availableTicketStatuses.includes(s));
  }

  await fetchAllLoans();
  await fetchTickets();

  allLoans.value = loans.value ?? [];
  allTickets.value = tickets.value ?? [];

  loanApplications.value = allLoans.value.map(loan => ({
    id: loan.loanId,
    title: loan.purpose,
    appliedOn: formatDate(loan.applicationDate),
    amount: loan.amount,
    term: loan.tenureMonths,
    status: loan.status,
    statusClass: getLoanStatusClass(loan.status),
  }));

  supportTickets.value = allTickets.value.map(ticket => ({
    id: ticket.d,
    subject: ticket.subject,
    createdAt: ticket.createdAt,
    status: ticket.status,
    statusClass: getTicketStatusClass(ticket.status),
  }));

  updateStats();

  if (selectedTab.value === 'loans') {
    applyLoanFilters();
  } else {
    applyTicketFilters();
  }
});

watch(selectedTab, newTab => {
  const query = { ...route.query, tab: newTab };

  if (newTab === 'loans') {
    query.statuses = selectedLoanStatuses.value.join(',');
    applyLoanFilters();
  } else if (newTab === 'tickets') {
    query.ticketStatuses = selectedTicketStatuses.value.join(',');
    applyTicketFilters();
  }

  router.replace({ query });
});

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
  switch (status.toUpperCase()) {
    case 'RESOLVED': return 'text-bg-success';
    case 'OPEN': return 'text-bg-secondary';
    case 'CLOSED': return 'text-bg-dark';
    default: return 'text-bg-light';
  }
};
</script>
