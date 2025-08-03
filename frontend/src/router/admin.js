import AdminDashboard from '@/pages/admin/Dashboard.vue';
import AdminLoan from '@/pages/admin/AdminLoan.vue';
import AdminLoanList from '@/pages/admin/AdminLoanList.vue';
import AdminTicket from '@/pages/admin/AdminTicket.vue';
import AdminTicketList from '@/pages/admin/AdminTicketList.vue';

export default [
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: AdminDashboard,
  },
  {
    path: '/admin/loans',
    name: 'AdminLoanList',
    component: AdminLoanList,
  },
  {
    path: '/admin/loan/:id',
    name: 'AdminLoan',
    component: AdminLoan,
  },
  {
    path: '/admin/tickets',
    name: 'AdminTicketList',
    component: AdminTicketList,
  },
  {
    path: '/admin/ticket/:id',
    name: 'AdminTicket',
    component: AdminTicket,
  },
];
