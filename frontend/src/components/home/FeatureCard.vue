<template>
    <div class="card shadow-sm p-4 m-3 text-center" style="width: 100%; max-width: 350px;">
      <div class="mb-4">
        <i :class="[iconClass, 'fs-1', 'text-primary']"></i>
      </div>
      <h2 class="h5 fw-bold mb-2">{{ title }}</h2>
      <p class="text-muted mb-0">{{ description }}</p>
    </div>
  </template>

  <script>
  export default {
    name: 'CardComponent',
    props: {
      iconClass: { type: String, required: true },
      title: { type: String, required: true },
      description: { type: String, required: true }
    }
  }
  </script>
