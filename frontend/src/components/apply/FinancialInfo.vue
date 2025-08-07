<template>
  <div class="card p-4 mx-auto w-75">
    <h4>Financial Information</h4>
    
    <div class="row mb-3">
      <!-- Monthly Income -->
      <div class="col">
        <label>Monthly Income *</label>
        <input
          v-model="localForm.income"
          type="text"
          class="form-control"
          :class="{ 'is-invalid': (incomeTouched || submitted) && !validIncome }"
          placeholder="Enter your monthly income"
          required
          @input="onIncomeInput"
        />
        <div class="invalid-feedback">
          Please enter a valid number greater than ₹25,000.
        </div>
      </div>
      
      <!-- Credit Score -->
      <div class="col">
        <label>Credit Score (if known)</label>
        <input
          v-model="localForm.creditScore"
          type="number"
          class="form-control"
          :class="{ 'is-invalid': (creditScoreTouched || submitted) && !validCreditScore }"
          placeholder="300 - 900"
          @input="onCreditScoreInput"
        />
        <div class="invalid-feedback">
          Credit score must be between 300 and 900.
        </div>
      </div>
    </div>
    
    <div class="d-flex justify-content-between mt-3">
      <button class="btn btn-outline-secondary" @click="$emit('prev')">Previous</button>
      <button class="btn btn-primary" @click="handleNext">Next</button>
    </div>
  </div>
</template>

<script>
export default {
  props: ['form'],
  emits: ['next', 'prev', 'update:form'],
  data() {
    return {
      localForm: { ...this.form },
      submitted: false,
      incomeTouched: false,
      creditScoreTouched: false,
    };
  },
  watch: {
    localForm: {
      handler(val) {
        this.$emit('update:form', val);
      },
      deep: true,
    }
  },
  computed: {
    validIncome() {
      const incomeStr = String(this.localForm.income).replace(/,/g, '').trim();
      if (!incomeStr || isNaN(incomeStr)) return false;
      const incomeNum = Number(incomeStr);
      return incomeNum > 25000;
    },
    validCreditScore() {
      if (!this.localForm.creditScore) return true;  
      const score = Number(this.localForm.creditScore);
      return score >= 300 && score <= 900;
    }
  },
  methods: {
    handleNext() {
      this.submitted = true;
      this.incomeTouched = true;
      this.creditScoreTouched = true;
      
      if (this.validIncome && this.validCreditScore) {
        this.$emit('next');
      }
    },
    onIncomeInput() {
      this.incomeTouched = true;
    },
    onCreditScoreInput() {
      this.creditScoreTouched = true;
    }
  }
};
</script>

 