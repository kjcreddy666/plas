<!-- Auth.vue(Presentation) -->

<template>
    <div class="container py-5">
      <div class="text-center mb-4">
        <h2 class="mb-4"><strong>LoanFlow</strong></h2>

        <h3 class="mb-1" v-if="tab === 'login'">Welcome Back</h3>
        <h3 class="mb-1" v-else>Create Account</h3>

        <p class="mb-4 fs-6" v-if="tab === 'login'">
          Sign in to your account to continue
        </p>
        <p class="mb-4 fs-6" v-else>
          Join LoanFlow to start your loan application journey
        </p>
      </div>

      <div class="card shadow-sm mx-auto" style="max-width: 500px">
        <div class="card-body">
          <component :is="tab === 'login' ? LoginForm : RegisterForm" />
        </div>
      </div>

      <div class="text-center mt-3">
        <template v-if="tab === 'login'">
          Don't have an account?
          <a href="#" @click.prevent="changeTab('register')">Create one now</a>
        </template>
        <template v-else>
          Already have an account?
          <a href="#" @click.prevent="changeTab('login')">Sign in here</a>
        </template>
      </div>

      <div class="text-center mt-4">
        <router-link to="/">← Back to Home</router-link>
      </div>
    </div>
  </template>


  <script setup>
  import { useRoute, useRouter } from 'vue-router'
  import { computed } from 'vue'
  import LoginForm from '../components/LoginForm.vue'
  import RegisterForm from '../components/RegisterForm.vue'

  const route = useRoute()
  const router = useRouter()

  const tab = computed(() => route.query.tab || 'login')

  function changeTab(newTab) {
    router.push({ query: { tab: newTab } })
  }
  </script>
