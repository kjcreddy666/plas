import UserDashboard from '@/pages/user/Dashboard.vue';
import NewLoan from '@/pages/user/NewLoan.vue';
import NewTicket from '@/pages/user/NewTicket.vue';
import UserLoan from '@/pages/user/UserLoan.vue';
import UserLoanList from '@/pages/user/UserLoanList.vue';
import UserTicket from '@/pages/user/UserTicket.vue';
import UserTicketList from '@/pages/user/UserTicketList.vue';

export default [
  {
    path: '/user',
    name: 'UserDashboard',
    component: UserDashboard,
  },
  {
    path: '/user/loans',
    name: 'UserLoanList',
    component: UserLoanList,
  },
  {
    path: '/user/loan/:id',
    name: 'UserLoan',
    component: UserLoan,
  },
  {
    path: '/user/new-loan',
    name: 'NewLoan',
    component: NewLoan,
  },
  {
    path: '/user/tickets',
    name: 'UserTicketList',
    component: UserTicketList,
  },
  {
    path: '/user/ticket/:id',
    name: 'UserTicket',
    component: UserTicket,
  },
  {
    path: '/user/new-ticket',
    name: 'NewTicket',
    component: NewTicket,
  },
];
