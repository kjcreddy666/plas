<template>
    <div class="text-center mx-auto w-50">
      <div class="alert alert-success">
        <h4>🎉 Application Submitted Successfully!</h4>
        <p>Thank you, <strong>{{ name }}</strong>. We’ve received your application.</p>
      </div>
      <div class="d-flex justify-content-center gap-3 mt-4">
        <button class="btn btn-outline-primary" @click="goHome">Home</button>
        <button class="btn btn-primary" @click="goDashboard">Dashboard</button>
      </div>
    </div>
  </template>

  <script>
  export default {
    props: ['name'],
    methods: {
      goHome() {
        this.$router.push('/');
      },
      goDashboard() {
        this.$router.push('/dashboard');
      }
    }
  }
  </script>
