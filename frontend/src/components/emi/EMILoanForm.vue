<template>
    <div class="border p-4 rounded shadow-sm">
      <h5 class="fw-bold mb-3"><i class="bi bi-calculator-fill me-2"></i>Loan Details</h5>
  
      <!-- Loan Amount -->
      <label class="form-label">Loan Amount</label>
      <input
        type="range"
        class="form-range"
        min="10000"
        max="10000000"
        step="1000"
        :value="amount"
        @input="$emit('update:amount', $event.target.valueAsNumber)"
      />
      <div class="d-flex justify-content-between mb-2">
        <small>₹10,000</small>
        <small class="fw-bold text-primary">₹{{ format(amount) }}</small>
        <small>₹1,00,00,000</small>
      </div>
  
      <!-- Interest Rate -->
      <label class="form-label">Interest Rate (% per annum)</label>
      <input
        type="range"
        class="form-range"
        min="5"
        max="20"
        step="0.1"
        :value="rate"
        @input="$emit('update:rate', $event.target.valueAsNumber)"
      />
      <div class="d-flex justify-content-between mb-2">
        <small>5%</small>
        <small class="fw-bold text-primary">{{ rate }}%</small>
        <small>20%</small>
      </div>
  
      <!-- Tenure -->
      <label class="form-label">Loan Tenure</label>
      <input
        type="range"
        class="form-range"
        min="6"
        max="360"
        step="1"
        :value="tenure"
        @input="$emit('update:tenure', $event.target.valueAsNumber)"
      />
      <div class="d-flex justify-content-between mb-3">
        <small>6 months</small>
        <small class="fw-bold text-primary">{{ tenure }} months ({{ Math.floor(tenure / 12) }} years)</small>
        <small>30 years</small>
      </div>
  
      <!-- Manual Inputs -->
      <div class="d-flex gap-2 mb-3">
        <input
          type="number"
          class="form-control"
          :value="amount"
          @input="$emit('update:amount', $event.target.valueAsNumber)"
          placeholder="Amount (₹)"
        />
        <input
          type="number"
          class="form-control"
          :value="rate"
          @input="$emit('update:rate', $event.target.valueAsNumber)"
          placeholder="Rate (%)"
          step="0.1"
        />
        <input
          type="number"
          class="form-control"
          :value="tenure"
          @input="$emit('update:tenure', $event.target.valueAsNumber)"
          placeholder="Tenure (months)"
        />
      </div>
  
      <!-- Calculate Button -->
      <button class="btn btn-primary w-100 fw-bold" @click="$emit('calculate')">
        Calculate EMI
      </button>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      amount: Number,
      rate: Number,
      tenure: Number
    },
    emits: ['update:amount', 'update:rate', 'update:tenure', 'calculate'],
    methods: {
      format(val) {
        return val.toLocaleString('en-IN');
      }
    }
  };
  </script>
  
  <style scoped>
  input[type="range"]::-webkit-slider-thumb {
    background-color: #6f42c1;
  }
  </style>
  