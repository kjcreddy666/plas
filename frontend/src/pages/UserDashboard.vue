<template>
  <div class="container py-4 min-vh-100">
    <DashboardHeader :user="user" />
    <StatsRow :stats="stats" />
    <div class="row g-4">
      <div class="col-md-8">
        <LoanApplications :loanApplications="loanApplications" />
      </div>
      <div class="col-md-4">
        <Sidebar :quickActions="quickActions" :supportTickets="supportTickets" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue';
import { useAuthGuard } from '@/composables/useAuthGuard'; // 👈 import the guard first

const { userId, token, role } = useAuthGuard(); // 👈 protect the route early

// Components
import DashboardHeader from '../components/dashboard/DashboardHeader.vue';
import StatsRow from '../components/dashboard/StatsRow.vue';
import LoanApplications from '../components/dashboard/LoanApplications.vue';
import Sidebar from '../components/dashboard/Sidebar.vue';

import { useLoan } from '@/composables/useLoan';
import { useTicket } from '@/composables/useTicket';

// Static data
const user = {
  name: localStorage.getItem('userName'),
  message: 'Track your loan applications and manage your account from your personal dashboard.',
};

const stats = ref([
  { label: 'Total Applications', value: 0, class: 'fw-bold' },
  { label: 'Approved', value: 0, class: 'text-success fw-bold' },
  { label: 'Pending', value: 0, class: 'text-warning fw-bold' },
  { label: 'Support Tickets', value: 0, class: 'fw-bold' },
]);

const quickActions = [
  { label: '+   Apply for New Loan', to: "/apply" },
  { label: '📄  Calculate EMI', to: "/emi-calculator" },
  { label: '❓  Get Support', to: "/support-ticket"},
  { label: '📈  View Profile', to: "/profile" },
];

const loanApplications = ref([]);
const supportTickets = ref([]);

const { loans, fetchLoansByUser } = useLoan();
const { tickets, fetchTicketsByUser } = useTicket();

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
    case 'Open': return 'text-bg-secondary';
    case 'Closed': return 'text-bg-dark';
    default: return 'text-bg-light';
  }
};

onMounted(async () => {
  if (!userId.value) return;

  await fetchLoansByUser(userId.value);
  await fetchTicketsByUser(userId.value);

  loanApplications.value = loans.value.map((loan) => ({
    id: loan.id,
    title: loan.purpose,
    appliedOn: formatDate(loan.applicationDate),
    amount: loan.amount,
    term: loan.tenureMonths,
    status: loan.status,
    statusClass: getLoanStatusClass(loan.status),
  }));

  supportTickets.value = tickets.value.map((ticket) => ({
    ticketId: ticket.id,
    subject: ticket.subject,
    date: formatDate(ticket.createdAt),
    status: ticket.status,
    statusClass: getTicketStatusClass(ticket.status),
  }));

  const approvedCount = loans.value.filter((l) => l.status === 'APPROVED').length;
  const pendingCount = loans.value.filter((l) => l.status === 'NEW' || l.status === 'UNDER_REVIEW').length;

  stats.value = [
    { label: 'Total Applications', value: loans.value.length, class: 'fw-bold' },
    { label: 'Approved', value: approvedCount, class: 'text-success fw-bold' },
    { label: 'UNDER', value: pendingCount, class: 'text-warning fw-bold' },
    { label: 'Support Tickets', value: tickets.value.length, class: 'fw-bold' },
  ];
});
</script>

