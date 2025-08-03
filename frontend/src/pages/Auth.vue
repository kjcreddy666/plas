<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import Login from '@/components/Login.vue'
import Register from '@/components/Register.vue'

const route = useRoute()

const currentTab = computed(() => {
  const tab = route.query.tab
  return tab === 'register' ? 'register' : 'login' // default to login
})
</script>

<template>
  <component :is="currentTab === 'login' ? Login : Register" />
</template>