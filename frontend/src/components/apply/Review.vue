<template>
    <div class="card p-4 mx-auto w-75">
      <h4>Review Your Application</h4>
      <div class="row">
        <div class="col">
          <h6>Personal Information</h6>
          <p><strong>Name:</strong> {{ form.name }}</p>
          <p><strong>Email:</strong> {{ form.email }}</p>
        </div>
        <div class="col">
          <h6>Loan Details</h6>
          <p><strong>Amount:</strong> {{ form.amount }}</p>
          <p><strong>Term:</strong> {{ form.tenureMonths }}</p>
          <p><strong>Purpose:</strong> {{ form.purpose }}</p>
        </div>
      </div>
      <div class="alert alert-info mt-3">
        By submitting this application, you agree to our terms and authorize us to verify the provided information.
      </div>
      <div class="d-flex justify-content-between mt-3">
        <button class="btn btn-outline-secondary" @click="$emit('prev')">Previous</button>
        <button class="btn btn-success" @click="$emit('submit')">Submit Application</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: ['form'],
    emits: ['submit', 'prev']
  }
  </script>
  