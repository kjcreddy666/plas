import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/pages/Home.vue';
import Auth from '@/pages/Auth.vue';
import Dashboard from '@/pages/Dashboard.vue';
import EMI from '@/pages/EMI.vue';
import CustomerSupport from '@/pages/CustomerSupport.vue';
import Apply from '@/pages/Apply.vue';
import UserLoan from '@/pages/UserLoan.vue';
import UserTicket from '@/pages/UserTicket.vue';
import Profile from '@/pages/Profile.vue';
import NotFound from '@/pages/NotFound.vue';
import Unauthorized from '@/pages/Unauthorized.vue';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/auth', name: 'Auth', component: Auth },
  { path: '/emi-calculator', name: 'EMI Calculator', component: EMI },
  { path: '/apply', name: 'New Application', component: Apply },
  { path: '/dashboard', name: 'Dashboard', component: Dashboard },
  { path: '/support-ticket', name: 'Support Ticket', component: CustomerSupport },
  { path: '/loans/:id', name: 'Loan Details', component: UserLoan },
  { path: '/ticket/:id', name: 'Ticket Details', component: UserTicket },
  { path: '/profile', name: 'Profile', component: Profile },
  
  // Add these two routes at the end
  { path: '/unauthorized', name: 'Unauthorized', component: Unauthorized },
  { path: '/:pathMatch(.*)*', name: 'NotFound', component: NotFound },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
