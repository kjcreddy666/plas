import { createRouter, createWebHistory } from 'vue-router';
import Home from '@/pages/Home.vue';
import Auth from '@/pages/Auth.vue';

import userRoutes from './user';
import adminRoutes from './admin';

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/auth', name: 'Auth', component: Auth },
  ...userRoutes,
  ...adminRoutes,
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
