<template>
    <div class="container">
      <div class="card p-4 mx-auto w-75">
        <h4>Personal Information</h4>
        <div class="row mb-3">
          <div class="col">
            <label>Full Name *</label>
            <input v-model="localForm.name" type="text" class="form-control" required />
          </div>
          <div class="col">
            <label>Email Address *</label>
            <input v-model="localForm.email" type="email" class="form-control" required />
          </div>
        </div>
        <div class="d-flex justify-content-between mt-3">
          <button class="btn btn-outline-secondary" disabled>Previous</button>
          <button class="btn btn-primary" @click="$emit('next')">Next</button>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: ['form'],
    emits: ['next', 'update:form'],
    data() {
      return {
        localForm: { ...this.form }
      };
    },
    watch: {
      localForm: {
        handler(val) {
          this.$emit('update:form', val);
        },
        deep: true
      }
    }
  };
  </script>
  