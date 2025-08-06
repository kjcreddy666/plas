<template>
  <div>
    <Navbar v-if="!isAuthPage" />
    <router-view />
    <Footer v-if="!isAuthPage" />
  </div>
</template>

<script setup>
import { useRoute } from 'vue-router';
import { computed } from 'vue';

import Navbar from './components/Navbar.vue';
import Footer from './components/Footer.vue';

const route = useRoute();
const isAuthPage = computed(() => route.path === '/auth');
</script>

<style scoped>
/* Your styles here */
</style>
