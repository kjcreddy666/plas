import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/pages/Home.vue';
import Auth from '@/pages/Auth.vue';
import UserDashboard from '@/pages/UserDashboard.vue';
import AdminDashboard from '@/pages/AdminDashboard.vue';
import EMI from '@/pages/EMI.vue';
import CustomerSupport from '@/pages/CustomerSupport.vue';
import Apply from '@/pages/Apply.vue';
import UserLoan from '@/pages/UserLoan.vue';
import UserTicket from '@/pages/UserTicket.vue';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/auth', name: 'Auth', component: Auth },
  { path: '/emi-calculator', name: 'EMI Calculator', component: EMI },
  { path: '/apply', name: 'New Application', component: Apply },
  { 
    path: '/dashboard', 
    name: 'Dashboard', 
    component: UserDashboard,
    beforeEnter: (to, from, next) => {
      const role = localStorage.getItem('userRole');
      if (role === 'ADMIN') {
        to.matched[0].components.default = AdminDashboard;
      }
      next();
    }
  },
  { path: '/support-ticket', name: 'Support Ticket', component: CustomerSupport},
  { path: '/loans/:id', name: 'Loan Details', component: UserLoan },
  { path: '/ticket/:id', name: 'Ticket Details', component: UserTicket }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
