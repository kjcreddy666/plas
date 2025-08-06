<template>
    <div>
      <!-- EMI Breakdown Card -->
      <div class="border p-4 rounded shadow-sm mb-4">
        <h5 class="fw-bold mb-3">
          <i class="bi bi-graph-up-arrow me-2"></i>EMI Breakdown
        </h5>
  
        <div class="mb-2 d-flex justify-content-between">
          <span><i class="bi bi-currency-rupee me-2"></i>Monthly EMI</span>
          <span class="fw-bold text-primary">₹{{ format(emi) }}</span>
        </div>
        <div class="mb-2 d-flex justify-content-between">
          <span><i class="bi bi-percent me-2"></i>Total Interest</span>
          <span class="fw-bold">₹{{ format(interest) }}</span>
        </div>
        <div class="mb-2 d-flex justify-content-between">
          <span><i class="bi bi-calendar me-2"></i>Total Amount</span>
          <span class="fw-bold">₹{{ format(total) }}</span>
        </div>
      </div>
  
      <!-- Payment Breakdown Card -->
      <div class="border p-4 rounded shadow-sm">
        <h6 class="fw-bold mb-3">Payment Breakdown</h6>
  
        <div class="mb-3">
          <div class="d-flex justify-content-between">
            <small>Principal Amount</small>
            <small>₹{{ format(amount) }}</small>
          </div>
          <div class="progress" style="height: 10px;">
            <div class="progress-bar bg-primary" :style="{ width: principalPercent + '%' }"></div>
          </div>
        </div>
  
        <div>
          <div class="d-flex justify-content-between">
            <small>Interest Amount</small>
            <small>₹{{ format(interest) }}</small>
          </div>
          <div class="progress" style="height: 10px;">
            <div class="progress-bar bg-secondary" :style="{ width: interestPercent + '%' }"></div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: ['emi', 'total', 'interest', 'amount'],
    computed: {
      principalPercent() {
        return (this.amount / this.total) * 100;
      },
      interestPercent() {
        return (this.interest / this.total) * 100;
      }
    },
    methods: {
      format(val) {
        return val.toLocaleString('en-IN');
      }
    }
  }
  </script>
  